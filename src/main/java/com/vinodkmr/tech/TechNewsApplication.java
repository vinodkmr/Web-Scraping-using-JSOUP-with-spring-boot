package com.vinodkmr.tech;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class TechNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechNewsApplication.class, args);
	}
	
	

}
