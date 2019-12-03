package com.daimeng.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.daimeng.util.Constants;

@Component
public class Scheduler2Task {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        Constants.println("现在时间：" + dateFormat.format(new Date()));
    }
}
