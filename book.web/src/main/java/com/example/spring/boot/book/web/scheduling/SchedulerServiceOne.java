/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.scheduling;

import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class SchedulerServiceOne {
    
    @Scheduled(initialDelay = 5000, fixedDelay = 9000) 
//	@Scheduled(initialDelayString = "5000" ,fixedDelayString = "9000")
	// 1000 milli sec = 1sec
	public void scheduledMethod() {
		System.out.println("Hello Scheduler One :" +new Date());
	}
}
