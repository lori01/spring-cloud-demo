package com.daimeng.api.user.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.daimeng.api.user.entity.SysUser;
import com.daimeng.api.user.repository.SysUserRepository;
import com.daimeng.api.user.service.UserService;
import com.daimeng.api.utils.Constants;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private SysUserRepository userRepository;
	
	@Override
	public ArrayList<SysUser> getAll() {
		ArrayList<SysUser> list = (ArrayList) userRepository.findAll();
		return list;
	}


	@Override
	public ArrayList<SysUser> getUserByLoginName(SysUser user) {
		ArrayList<SysUser> list = userRepository.findByLoginName(user);
		return list;
	}

	@Override
	public void saveUser(SysUser user) {
		userRepository.save(user);
		if(user.getPassword() == null || "".equals(user.getPassword()) ||
				user.getLoginName() == null || "".equals(user.getLoginName())){
			throw new RuntimeException("发生异常,用户名或密码为空");
		}
	}


	@Override
	public Page<SysUser> getPage(Integer pageNum) {
		PageRequest pageable = new PageRequest(pageNum, Constants.PAGE_SIZE);
		Page<SysUser> list = userRepository.findAll(pageable);
		return list;
	}

}
