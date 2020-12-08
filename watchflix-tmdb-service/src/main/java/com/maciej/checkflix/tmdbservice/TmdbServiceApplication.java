package com.maciej.checkflix.tmdbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TmdbServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmdbServiceApplication.class, args);
	}

}
