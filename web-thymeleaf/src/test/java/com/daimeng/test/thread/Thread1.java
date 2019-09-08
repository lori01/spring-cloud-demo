package com.daimeng.test.thread;

import java.util.Date;

import com.daimeng.util.DateUtils;

public class Thread1 extends Thread{
	public void run(){
		try {
			int i = 0;
			while(i++ < 5){
				Thread.sleep(2000);
				System.out.println("t1:" + DateUtils.getDateStrFormat(new Date(), "HH:mm:ss:SSS"));
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
