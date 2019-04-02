package com.daimeng.api.user.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daimeng.api.user.entity.UserEntity;
import com.daimeng.api.user.mapper.UserMapper;
import com.daimeng.api.user.service.UserService;
import com.daimeng.api.utils.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public ArrayList<UserEntity> getAll() {
		try {
			ArrayList<UserEntity> list = userMapper.getAll();
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<UserEntity> getUserByLoginName(UserEntity user) {
		try {
			ArrayList<UserEntity> list = userMapper.getUserByLoginName(user);
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * springboot实现事务只需要	在头上加上@Transactional注解
	 * @Transactional 默认只捕获RuntimeException.class
	 * 对Exception异常得需要 @Transactional(rollbackFor = {Exception.class}) 捕获回滚
	 * 或者使用全局AOP配置 TransactionAdviceConfig
	 */
	//@Transactional
	@Override
	public void saveUser(UserEntity user) {
		userMapper.saveUser(user);
		if(user.getPassword() == null || "".equals(user.getPassword()) ||
				user.getLoginName() == null || "".equals(user.getLoginName())){
			throw new RuntimeException("发生异常,用户名或密码为空");
		}
		Long count = userMapper.countUserByLoginName(user.getLoginName());
		if(count != null && count > 1){
			throw new RuntimeException("发生异常,用户名已存在");
		}
	}

	@Override
	public PageInfo<UserEntity> getPage(Integer pageNum) {
		//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper/dao接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
		ArrayList<UserEntity> list = userMapper.getAll();
		PageInfo<UserEntity> pageInfo = new PageInfo<UserEntity>(list);
		//System.out.println(pageInfo.getTotal());
		return pageInfo;
	}

}
