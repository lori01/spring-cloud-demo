package com.daimeng.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.daimeng.util.Constants;

import daemon.DeamonThread;

@Component
@Order(1)
public class OrderRunner1 extends Thread  implements CommandLineRunner {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void run(String... args) throws Exception {
		Constants.println("The OrderRunner1 start to initialize ...");
		Constants.println("启动时间：" + dateFormat.format(new Date()));
		
		/*System.out.println("main thread start");
		Thread thread=new OrderRunner1();
		thread.setDaemon(true);
		thread.start();
		System.out.println("main thread end");*/
	}
	
	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				Thread.sleep(1000);
				System.out.println("DeamonThread run:"+i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
