package com.daimeng.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.daimeng.util.Constants;

@Component
@Order(2)
public class OrderRunner2 implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		Constants.println("The OrderRunner2 start to initialize ...");
	}

}
