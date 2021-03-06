package com.daimeng.web.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.SysUserLog;
import com.daimeng.web.user.vo.UserVO;

public interface UserService {
	//public ArrayList<UserVO> getUserByParameter(UserVO user);
	
	public Page<SysUser> getUserPage(Integer pageNum);
	public SysUser findSysUser(int id);
	public ResponseVo updateUserBscInf(SysUser user);
	public ResponseVo updateUserPd(Integer uid,String oldPwd,String newPwd);
	public ResponseVo addUser(SysUser user);
	
	public Page<SysUser> findAllBySpecification(SysUser info,int page);
	
	public Page<SysUserLog> getUserLogPage(SysUserLog info, int page);
	public List<SysRole> findAllRole();
	
	public void saveSysUserLog(HttpServletRequest request,long executeTime);
	public String getIpAddress(String ip);
}
