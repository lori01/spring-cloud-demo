package com.daimeng.interceptor;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.daimeng.util.Constants;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.SysUserLog;
import com.daimeng.web.user.repository.SysUserLogRepository;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);
	
	@Autowired
	private SysUserLogRepository sysUserLogRepository;
	
	/**
     * 前置检查,方法执行前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ip = request.getRemoteAddr();
        Instant startTime = Instant.now();
        request.setAttribute("logrequestStartTime", startTime);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取用户token
        Method method = handlerMethod.getMethod();
        LOGGER.info("用户:"+ip+",访问目标:"+method.getDeclaringClass().getName() + "." + method.getName());
        return true;
    }

    /**
     *  方法执行中
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    // controller处理完成
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Instant startTime = (Instant) request.getAttribute("logrequestStartTime");

        Instant endTime = Instant.now();
        long executeTime = endTime.toEpochMilli()- startTime.toEpochMilli();

        // log it
        if (executeTime > 1000) {
            LOGGER.info("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        } else {
            LOGGER.info("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        }
        
        try {
        	SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
        	if(cuser != null){
        		SysUserLog userlog = new SysUserLog();
    			userlog.setUid(cuser.getId());
    			userlog.setCreateTm(new Date());
    			userlog.setUrl(request.getRequestURL().toString());
    			userlog.setParameter(request.getRequestURI());
    			userlog.setExecuteTime(executeTime);
    			userlog.setSysUser(cuser);
    			sysUserLogRepository.save(userlog);
        	}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

    }
    
    @Override
    public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
}
