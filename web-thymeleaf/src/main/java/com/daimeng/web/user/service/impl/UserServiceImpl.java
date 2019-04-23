package com.daimeng.web.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.daimeng.util.Constants;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.SysUserLog;
import com.daimeng.web.user.entity.SysUserRole;
import com.daimeng.web.user.entity.SysUserRoleId;
import com.daimeng.web.user.mapper.UserMapper;
import com.daimeng.web.user.repository.SysRoleRepository;
import com.daimeng.web.user.repository.SysUserLogRepository;
import com.daimeng.web.user.repository.SysUserRepository;
import com.daimeng.web.user.repository.SysUserRoleRepository;
import com.daimeng.web.user.service.UserService;
import com.daimeng.web.user.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{

	@Value("${shiro.password.algorithmName}")
    private String algorithmName;//设置算法
	@Value("${shiro.password.hashIterations}")
    private int hashIterations;//生成Hash值的迭代次数
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SysUserRepository userRepository;
	@Autowired
	private SysUserLogRepository userLogRepository;
	@Autowired
	private SysUserRoleRepository userRoleRepository;
	@Autowired
	private SysRoleRepository roleRepository;
	
	@Override
	public Page<SysUser> findAllBySpecification(final SysUser info, int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Specification specification = new Specification<SysUser>() {
			@Override
			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb){
				List<Predicate> list = new ArrayList<Predicate>();
				if(info.getLoginName() != null && !"".equals(info.getLoginName())){
					list.add(cb.equal(root.get("loginName"), info.getLoginName()));
				}
				if(info.getSexCd() != null && !"".equals(info.getSexCd())){
					list.add(cb.equal(root.get("sexCd"), info.getSexCd()));
				}
				if(info.getEmail() != null && !"".equals(info.getEmail())){
					list.add(cb.equal(root.get("email"), info.getEmail()));
				}
				if(info.getPhone() != null && !"".equals(info.getPhone())){
					list.add(cb.equal(root.get("phone"), info.getPhone()));
				}
				if(info.getRealname() != null && !"".equals(info.getRealname())){
					list.add(cb.like((Expression) root.get("realname"), "%" +info.getRealname()+ "%"));
				}
				query.where(list.toArray(new Predicate[list.size()]));
				query.orderBy(cb.asc(root.get("id")));
				return query.getRestriction();
			}
		};
		Page<SysUser> pagelist = userRepository.findAll(specification, pageable);
		for(SysUser user : pagelist){
			SysUserLog log = userLogRepository.findFirstByUidOrderByIdDesc(user.getId());
			if(log != null){
				user.setLastActionTm(log.getCreateTm());
			}
		}
		
		return pagelist;
	}
	
	@Override
	public Page<SysUser> getUserPage(Integer pageNum) {
		/*Pageable pageable = new PageRequest(pageNum, Constants.PAGE_SIZE_5);
		return userRepository.findAll(pageable);*/
		SysUser info = new SysUser();
		return findAllBySpecification(info, pageNum);
	}
	
	/*@Override
	public ArrayList<UserVO> getUserByParameter(UserVO user) {
		return userMapper.getUserByParameter(user);
	}*/

	@Override
	public SysUser findSysUser(int id) {
		return userRepository.findOne(id);
	}

	@Override
	//user的事务无法自动生效,必须使用transactional,不知道为什么
	@Transactional
	public ResponseVo updateUserBscInf(SysUser user) {
		ResponseVo vo = new ResponseVo();
		if(user != null){
			SysUser cur = findSysUser(user.getId());
			if(cur != null){
				cur.setPhone(user.getPhone());
				cur.setEmail(user.getEmail());
				cur.setRealname(user.getRealname());
				cur.setImg(user.getImg());
				cur.setSexCd(user.getSexCd());
				userRepository.save(cur);
				
				/*UserVO uvo = new UserVO();
				uvo.setPhone(user.getPhone());
				uvo.setEmail(user.getEmail());
				uvo.setRealName(user.getRealname());
				uvo.setImg(user.getImg());
				uvo.setSexCd(user.getSexCd());
				uvo.setId(cur.getId());
				userMapper.updateUser(uvo);*/
				
				vo.setStatus(100);
				vo.setDesc("更新成功!");
				vo.setObj(cur);
				
				return vo;
			}
		}
		vo.setStatus(200);
		vo.setDesc("更新失败!");
		return vo;
	}
	
	public static void main(String[] args) {
		
	}
	
	public static String getNewPassword(String login, String passwd, String salt){
		String newPassword = new SimpleHash("md5",passwd,ByteSource.Util.bytes(login+salt),1).toHex();
		System.out.println(newPassword);
		return newPassword;
	}

	@Override
	@Transactional
	public ResponseVo updateUserPd(Integer uid,String oldPwd,String newPwd) {
		ResponseVo vo = new ResponseVo();
		if(uid != null){
			SysUser cur = findSysUser(uid);
			if(cur != null){
				String oldpd = getNewPassword(cur.getLoginName(), oldPwd, cur.getSalt());
				if(!oldpd.equals(cur.getPassword())){
					vo.setStatus(200);
					vo.setDesc("原密码错误!");
					return vo;
				}else{
					String newpd = getNewPassword(cur.getLoginName(), newPwd, cur.getSalt());
					cur.setPassword(newpd);
					userRepository.save(cur);
					vo.setStatus(100);
					vo.setDesc("更新成功!");
					vo.setObj(cur);
					
					return vo;
				}
			}
		}
		return vo;
	}
	
	@Override
	@Transactional
	public ResponseVo addUser(SysUser user) {
		ResponseVo vo = new ResponseVo();
		if(user != null){
			Long count = userRepository.countByLoginName(user.getLoginName());
			if(count != null && count > 0){
				vo.setStatus(200);
				vo.setDesc("登录名已存在!");
				return vo;
			}else{
				String salt = getNewPassword(user.getLoginName(), user.getPassword(), user.getLoginName());
				String newpd = getNewPassword(user.getLoginName(), user.getPassword(), salt);
				user.setSalt(salt);
				user.setPassword(newpd);
				userRepository.save(user);
				
				SysUserRole role = new SysUserRole();
				role.setRoleId(Integer.valueOf(user.getPermission()));
				role.setUid(user.getId());
				role = userRoleRepository.save(role);
				
				vo.setStatus(100);
				vo.setDesc("新增成功!");
				vo.setObj(user);
				return vo;
			}
		}
		vo.setStatus(200);
		vo.setDesc("新增失败!");
		return vo;
	}

	@Override
	public Page<SysUserLog> getUserLogPage(final SysUserLog info, int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Specification specification = new Specification<SysUserLog>() {
			@Override
			public Predicate toPredicate(Root<SysUserLog> root, CriteriaQuery<?> query, CriteriaBuilder cb){
				List<Predicate> list = new ArrayList<Predicate>();
				if(info.getUid() != null){
					list.add(cb.equal(root.get("uid"), info.getUid()));
				}
				if(info.getUrl() != null && !"".equals(info.getUrl())){
					list.add(cb.like((Expression) root.get("url"), "%" +info.getUrl()+ "%"));
				}
				query.where(list.toArray(new Predicate[list.size()]));
				query.orderBy(cb.desc(root.get("id")));
				return query.getRestriction();
			}
		};
		Page<SysUserLog> pagelist = userLogRepository.findAll(specification, pageable);
		
		return pagelist;
	}

	@Override
	public List<SysRole> findAllRole() {
		List<SysRole> list = roleRepository.findAll();
		return list;
	}

	@Override
	@Transactional
	public void saveSysUserLog(HttpServletRequest request,long executeTime) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
    	if(cuser != null){
    		SysUserLog userlog = new SysUserLog();
			userlog.setUid(cuser.getId());
			userlog.setCreateTm(new Date());
			userlog.setUrl(request.getRequestURL().toString());
			userlog.setParameter(request.getRequestURI());
			userlog.setExecuteTime(executeTime);
			userlog.setSysUser(cuser);
			userLogRepository.save(userlog);
    	}
		
	}

	

}
