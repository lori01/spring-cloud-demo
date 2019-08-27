package com.daimeng.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 首先要配置的是 ShiroConfig 类，Apache Shiro 核心通过 Filter 来实现，就好像 SpringMvc 通过 DispachServlet 来主控制一样。 
 * 既然是使用 Filter 一般也就能猜到，是通过 URL 规则来进行过滤和权限校验，所以我们需要定义一系列关于 URL 的规则和访问权限
 * @author daimeng
 *
 */
@Configuration
public class ShiroConfig {

	@Value("${shiro.password.algorithmName}")
    private String algorithmName;//设置算法
	@Value("${shiro.password.hashIterations}")
    private int hashIterations;//生成Hash值的迭代次数
	
	/**
	 * Filter Chain 定义说明：
	 * 1、一个URL可以配置多个 Filter，使用逗号分隔
	 * 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如 perms，roles
	 * 
	 * Shiro 内置的 FilterChain
	 * anon:所有 url 都都可以匿名访问
	 * authc: 需要认证才能进行访问
	 * user:配置记住我或认证通过可以访问
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("userFilter", new SysUserFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        
		//url拦截器
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		// 配置不会被拦截的链接 顺序判断
		//修复登录成功后跳转/favicon.ico页面的问题
		//因为如果ico没有anon权限,这注销后可能还会请求/favicon.ico,所以登录后直接跳转到/favicon.ico
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		filterChainDefinitionMap.put("/", "anon");
		//登录跳转判断，为了判断是否登录，登录则跳转首页，未登录则跳转登录页面
		filterChainDefinitionMap.put("/logins", "anon");
		//静态资源
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/echarts/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/ico/**", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		//百度富文本，资源保存地址
		filterChainDefinitionMap.put("/ueditor/**", "anon");
		filterChainDefinitionMap.put("/ueditor.config.js", "anon");
		filterChainDefinitionMap.put("/themes/default/css/ueditor.min.css", "anon");
		//未登录的首页
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/index/**", "anon");
		//spring boot自带监控页面
		filterChainDefinitionMap.put("/health", "anon");
		filterChainDefinitionMap.put("/info", "anon");
		filterChainDefinitionMap.put("/config", "anon");
		
		filterChainDefinitionMap.put("/api/**", "anon");
		//阿里巴巴数据连接监控
		filterChainDefinitionMap.put("/druid/**", "anon");
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		//anon:所有 url 都都可以匿名访问
		//authc: 需要认证才能进行访问
		//user:配置记住我或认证通过可以访问
		filterChainDefinitionMap.put("/**", "authc,userFilter");
		
		//页面权限控制
		//就说明访问/user/detail这个链接必须要有“权限添加”这个权限才可以访问
		//filterChainDefinitionMap.put("/user/detail", "perms[权限修改]");
		//说明访问/user/detail这个链接必须要有“权限添加”这个权限和具有“100001”这个角色才可以访问
		//filterChainDefinitionMap.put("/user/detail", "roles[100001],perms[权限修改]");
		//说明访问/user/detail这个链接必须要有“100001”这个角色才可以访问
		//filterChainDefinitionMap.put("/user/detail/*", "roles[100001]");
		
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		//shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setSuccessUrl("/article/list/1");

		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	/**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean()
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        //散列算法:MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512等。
        credentialsMatcher.setHashAlgorithmName(algorithmName);
        //加密次数   认1次， 设置两次相当于 md5(md5(""));
        credentialsMatcher.setHashIterations(hashIterations);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     * Realms：用于进行权限信息的验证，我们自己实现
     * Realm 本质上是一个特定的安全 DAO：它封装与数据源连接的细节，得到Shiro 所需的相关的数据
     * 在配置 Shiro 的时候，你必须指定至少一个Realm 来实现认证（authentication）和/或授权（authorization）
     * @param matcher
     * @return
     */
	@Bean
	public MyShiroRealm myShiroRealm(HashedCredentialsMatcher matcher){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setAuthorizationCachingEnabled(false);
		myShiroRealm.setCredentialsMatcher(matcher);
		return myShiroRealm;
	}


	/**
	 * SecurityManager：管理所有Subject，SecurityManager 是 Shiro 架构的核心，配合内部安全组件共同组成安全伞
	 * Subject：当前用户，Subject 可以是一个人，但也可以是第三方服务、守护进程帐户、时钟守护任务或者其它–当前和软件交互的任何事件
	 * @param realm
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(Authorizer authorizer, MyShiroRealm realm){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		//securityManager.setAuthorizer(authorizer);
		securityManager.setRealm(realm);
		//securityManager.setRealm(myShiroRealm(matcher));
		return securityManager;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
	    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
	    return authorizationAttributeSourceAdvisor;
	}
}
