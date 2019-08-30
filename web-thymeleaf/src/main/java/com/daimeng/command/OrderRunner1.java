package com.daimeng.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.daimeng.util.Constants;

@Component
@Order(1)
public class OrderRunner1 implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		Constants.println("The OrderRunner1 start to initialize ...");
	}

}
