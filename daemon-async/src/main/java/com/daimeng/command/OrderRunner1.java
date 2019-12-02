package com.daimeng.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.daimeng.util.Constants;

@Component
@Order(1)
public class OrderRunner1 implements CommandLineRunner {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void run(String... args) throws Exception {
		Constants.println("The OrderRunner1 start to initialize ...");
		Constants.println("启动时间：" + dateFormat.format(new Date()));
	}

}
