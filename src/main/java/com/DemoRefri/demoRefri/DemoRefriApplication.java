package com.DemoRefri.demoRefri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoRefriApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRefriApplication.class, args);
	}

}
