package com.daimeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class DaemonAsyncApplication  {
	
	public static void main(String[] args) {
		SpringApplication.run(DaemonAsyncApplication.class, args);
	}


}
