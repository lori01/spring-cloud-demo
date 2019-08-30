package com.daimeng.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

	private int count=0;
	
	@Scheduled(cron="*/6 * * * * ?")
    private void process(){
        //Constants.println("this is scheduler task runing  "+(count++));
    }
}
