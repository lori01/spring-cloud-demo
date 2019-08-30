package com.daimeng.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.daimeng.util.Constants;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.service.UserService;



public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	
	/**
	 * Authorization（授权）：访问控制。比如某个用户是否具有某个操作的使用权限
	 */
	/**
	 * Shiro 的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
	 * 当访问到页面的时候，链接配置了相应的权限或者 Shiro 标签才会执行此方法否则不会执行，
	 * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回 null 即可。
	 * 在这个方法中主要是使用类：SimpleAuthorizationInfo进行角色的添加和权限的添加
	 */
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Constants.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        /*UserEntity user  = (UserEntity)principals.getPrimaryPrincipal();
        if(user.getId() > 0){
        	SysUser sysUser = userService.findSysUser(user.getId());
        	for(SysRole role : sysUser.getRoleList()){
                authorizationInfo.addRole(role.getRole());
                for(SysPermission p : role.getPermissions()){
                    authorizationInfo.addStringPermission(p.getPermission());
                }
            }
        }
        authorizationInfo.addStringPermission(user.getPermission());*/
        
        //当然也可以添加 set 集合：roles 是从数据库查询的当前用户的角色，stringPermissions 是从数据库查询的当前用户对应的权限
        //authorizationInfo.setRoles(roles);
        //authorizationInfo.setStringPermissions(stringPermissions);
        
        return authorizationInfo;
        
    }
	
	/**
	 * Authentication（认证）：用户身份识别，通常被称为用户“登录”
	 * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
	 */
	/**
	 * 在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。
	 * 因为在 Shiro 中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。通常情况下，在 Realm 中会直接从我们的数据源中获取 Shiro 需要的验证信息。
	 * 可以说，Realm 是专用于安全框架的 DAO. Shiro 的认证过程最终会交由 Realm 执行，这时会调用 Realm 的getAuthenticationInfo(token)方法
	 * 该方法主要执行以下操作:
	 * 1、检查提交的进行认证的令牌信息
	 * 2、根据令牌信息从数据源(通常为数据库)中获取用户信息
	 * 3、对用户信息进行匹配验证。
	 * 4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
	 * 5、验证失败则抛出AuthenticationException异常信息
	 * @author daimeng
	 *
	 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        Constants.println("登陆认证配置-->MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        if(username == null || "".equals(username)){
        	throw new UnknownAccountException();
        }
        Constants.println(token.getCredentials());
        if(token.getCredentials() == null || "".equals(token.getCredentials())){
        	throw new IncorrectCredentialsException();
        }
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        /*UserVO user = new UserVO();
        user.setLoginName(username);
        ArrayList<UserVO> userList = userService.getUserByParameter(user);
        if(userList == null || userList.size() == 0){
        	throw new UnknownAccountException();
        }else if(userList.size() > 1){
        	throw new UnknownAccountException();
        }*/
        
        SysUser user = new SysUser();
        user.setLoginName(username);
        Page<SysUser> userList = userService.findAllBySpecification(user, 0);
        if(userList == null || userList.getContent().size() == 0){
        	throw new UnknownAccountException();
        }else if(userList.getContent().size() > 1){
        	throw new UnknownAccountException();
        }
        
        Constants.println("----->>userInfo="+userList.getSize());
        user = userList.getContent().get(0);
        Constants.println("----->>loginname="+user.getLoginName());
        Constants.println("----->>password="+user.getPassword());
        Constants.println("----->>salt="+user.getSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		user.getLoginName(), //用户名
        		user.getPassword(), //密码
                ByteSource.Util.bytes(user.getLoginName()+user.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_USER, user);
        return authenticationInfo;
    }
    
}
