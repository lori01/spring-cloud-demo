package com.daimeng.web.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSONObject;
import com.daimeng.util.BaiduSnCal;
import com.daimeng.util.Constants;
import com.daimeng.util.HttpUtils;
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
	@Value("${get_ip_address_url}")
	private String ipAddressUrl;//ip转地址
	
	/*baidu_ak=tlrgpbkZkwnZnNPeBc77wNVwplSDKxYq
	baidu_ip_to_address_url=http://api.map.baidu.com/location/ip*/	
	@Value("${baidu_ak}")
	private String baiduAk;
	@Value("${baidu_sk}")
	private String baiduSk;
	@Value("${baidu_ip_to_address_url}")
	private String baiduAddressUrl;
	
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
				if(info.getAccessToken() != null && !"".equals(info.getAccessToken())){
					list.add(cb.equal(root.get("accessToken"), info.getAccessToken()));
				}
				if(info.getWeiboUid() != null && !"".equals(info.getWeiboUid())){
					list.add(cb.equal(root.get("weiboUid"), info.getWeiboUid()));
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
				if(cur.getImg() == null || "".equals(cur.getImg())){
					if(cur.getSexCd() != null && "0".equals(cur.getSexCd())){
						cur.setImg(Constants.DEFAULT_USER_IMG_F);
					}else cur.setImg(Constants.DEFAULT_USER_IMG_M);
					
				}
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
	
	public String getNewPassword(String login, String passwd, String salt){
		String newPassword = new SimpleHash(algorithmName,passwd,ByteSource.Util.bytes(login+salt),hashIterations).toHex();
		Constants.println(newPassword);
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
				if(user.getSexCd() != null && "0".equals(user.getSexCd())){
					user.setImg(Constants.DEFAULT_USER_IMG_F);
				}else user.setImg(Constants.DEFAULT_USER_IMG_M);
				
				SysRole role = roleRepository.findOne(Integer.valueOf(user.getPermission()));
				user.setRole(role);
				
				userRepository.save(user);
				
				/*SysUserRole role = new SysUserRole();
				role.setRoleId(Integer.valueOf(user.getPermission()));
				role.setUid(user.getId());
				role = userRoleRepository.save(role);*/
				
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
			userlog.setIp(getIpAddress(request));
			//userlog.setAddress(getIpAddress(userlog.getIp()));
			userlog.setSysUser(cuser);
			userLogRepository.save(userlog);
    	}
		
	}
	
	
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}

	@Override
	public String getIpAddress(String ip) {
		//ip = "218.192.3.42";
		if("127.0.0.1".equals(ip) || "localhost".equals(ip)){
			return "本地机器localhost";
		}
		if(ip != null && !"".equals(ip) && baiduAddressUrl != null && !"".equals(baiduAddressUrl)){
			String sn = "";
			try {
				sn = BaiduSnCal.getSnForIp(baiduAk, baiduSk, ip);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			/*
			 * {
			 * 		"address":"CN|\u5e7f\u4e1c|\u5e7f\u5dde|None|CERNET|0|0",
			 * 		"content":{
			 * 			"address_detail":{
			 * 				"province":"\u5e7f\u4e1c\u7701",
			 * 				"city":"\u5e7f\u5dde\u5e02",
			 * 				"district":"",
			 * 				"street":"",
			 * 				"street_number":"",
			 * 				"city_code":257
			 * 			},
			 * 			"address":"\u5e7f\u4e1c\u7701\u5e7f\u5dde\u5e02",
			 * 			"point":{
			 * 				"y":"2629614.08",
			 * 				"x":"12613487.11"
			 * 			}
			 * 		},
			 * 		"status":0
			 * }
			 */
			String tjson = HttpUtils.sendPost(baiduAddressUrl, "ak="+baiduAk+"&ip="+ip+"&sn="+sn);
			Map tmap = (Map) JSONObject.parse(tjson);
			if(tmap != null && (Integer)tmap.get("status") == 0){
				String address = (String)((Map)tmap.get("content")).get("address");
				return address;
			}
		}
		return null;
	}

	

}
