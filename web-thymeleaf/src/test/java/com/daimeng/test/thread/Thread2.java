package com.daimeng.test.thread;

import java.util.Date;

import com.daimeng.util.DateUtils;

public class Thread2 extends Thread{
	public void run(){
		try {
			int i = 0;
			while(i++ < 5){
				Thread.sleep(3000);
				System.out.println("t2:"+ DateUtils.getDateStrFormat(new Date(), "HH:mm:ss:SSS"));
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
