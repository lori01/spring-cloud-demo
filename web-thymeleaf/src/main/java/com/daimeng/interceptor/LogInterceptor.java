package com.daimeng.interceptor;

import java.lang.reflect.Method;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.daimeng.util.Constants;
import com.daimeng.web.user.service.UserService;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);
	
	@Value("${user.trans.log.flag}")
	private boolean userTransLogFlag;
	
	@Autowired
	private UserService userService;
	
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
        if(userTransLogFlag){
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
            	userService.saveSysUserLog(request, executeTime);
    		} catch (Exception e) {
    			Constants.println(e.getMessage());
    		}
        }

    }
    
    @Override
    public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
}
