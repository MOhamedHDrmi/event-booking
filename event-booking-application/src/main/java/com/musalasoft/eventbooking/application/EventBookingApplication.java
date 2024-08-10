package com.musalasoft.eventbooking.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.musalasoft.eventbooking")
@EntityScan("com.musalasoft.eventbooking.sql.entity")
@EnableJpaRepositories("com.musalasoft.eventbooking.sql.repository")
@EnableScheduling
@EnableAspectJAutoProxy
public class EventBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBookingApplication.class, args);
    }

}
