package com.daimeng.web.user.service.impl;

import java.util.ArrayList;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daimeng.util.Constants;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.UserEntity;
import com.daimeng.web.user.mapper.UserMapper;
import com.daimeng.web.user.repository.SysUserRepository;
import com.daimeng.web.user.service.UserService;

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
	
	public static void main(String[] args) {
		/*String login = "guest";
		String salt = "8b0477b73a371b5579aacd9a89bdf380";
		String password = "86fb24bcfc4ee83489faad2f4e0940df";
		String newPassword = getNewPassword(login, "123456", salt);
    	System.out.println(newPassword);
    	System.out.println(password.equals(newPassword));*/
	}
	public static String getNewPassword(String login, String passwd, String salt){
		String newPassword = new SimpleHash(
                "md5",
                passwd,
                ByteSource.Util.bytes(login+salt),
                1).toHex();
		System.out.println(newPassword);
		return newPassword;
	}

	@Override
	public Page<SysUser> getUserPage(Integer pageNum) {
		Pageable pageable = new PageRequest(pageNum, Constants.PAGE_SIZE_5);
		return userRepository.findAll(pageable);
	}

	@Override
	public SysUser findSysUser(int id) {
		return userRepository.findOne(id);
	}

	@Override
	public ResponseVo updateUserBscInf(SysUser user) {
		ResponseVo vo = new ResponseVo();
		if(user != null){
			SysUser cur = findSysUser(user.getId());
			if(cur != null){
				cur.setPhone(user.getPhone());
				cur.setEmail(user.getEmail());
				cur.setRealname(user.getRealname());
				userRepository.save(cur);
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

	@Override
	public ResponseVo updateUserPd(Integer uid,String oldPwd,String newPwd) {
		ResponseVo vo = new ResponseVo();
		if(uid != null){
			SysUser cur = findSysUser(uid);
			if(cur != null){
				String oldpd = getNewPassword(cur.getLoginName(), oldPwd, cur.getLoginName() + cur.getSalt());
				if(!oldpd.equals(cur.getPassword())){
					vo.setStatus(200);
					vo.setDesc("原密码错误!");
					return vo;
				}else{
					String newpd = getNewPassword(cur.getLoginName(), newPwd, cur.getLoginName() + cur.getSalt());
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
	public ArrayList<UserEntity> getUserByParameter(UserEntity user) {
		return userMapper.getUserByParameter(user);
	}
	@Override
	public ResponseVo add(SysUser user) {
		ResponseVo vo = new ResponseVo();
		if(user != null){
			String salt = getNewPassword(user.getLoginName(), user.getPassword(), user.getLoginName());
			String newpd = getNewPassword(user.getLoginName(), user.getPassword(), user.getLoginName()+salt);
			user.setSalt(salt);
			user.setPassword(newpd);
			userRepository.save(user);
			vo.setStatus(100);
			vo.setDesc("新增成功!");
			vo.setObj(user);
			return vo;
		}
		vo.setStatus(200);
		vo.setDesc("新增失败!");
		return vo;
	}
	

}
