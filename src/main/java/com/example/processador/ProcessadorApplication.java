package com.example.processador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ProcessadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessadorApplication.class, args);
	}

}
