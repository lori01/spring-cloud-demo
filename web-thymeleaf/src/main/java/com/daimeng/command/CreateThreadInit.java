package com.daimeng.command;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.daimeng.util.DateUtils;
import com.daimeng.util.NumberUtils;

/**
 * 
* @功能描述: 启动项目这运行的方法
* @名称: CreateThreadInit.java 
* @路径 com.daimeng.command 
* @作者 daimeng.fun
* @E-Mail sephy9527@qq.com
* @创建时间 2019年12月10日 上午10:42:30 
* @version V1.0
 */
@Component
public class CreateThreadInit extends Thread{

	private long index;
	@PostConstruct
	public void init(){
		CreateThreadInit ct = new CreateThreadInit();
		long random = NumberUtils.getRandomNumber(4);
		ct.setDaemon(true);
		ct.setIndex(random);
		ct.start();
	}
	public void run(){
		while(true){
			try {
				doMyService();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("===CreateThreadInit Error==="+ DateUtils.getDateStrFormat(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
			}
			
		}
		
	}
	public void doMyService(){
		System.out.println("CreateThreadInit Index : "+index+" ;Time : " + DateUtils.getDateStrFormat(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
	}
	public long getIndex() {
		return index;
	}
	public void setIndex(long index) {
		this.index = index;
	}
	
}
