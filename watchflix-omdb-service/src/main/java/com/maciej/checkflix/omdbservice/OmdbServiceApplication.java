package com.maciej.checkflix.omdbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OmdbServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmdbServiceApplication.class, args);
	}

}
