package com.maciej.checkflix.watchlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WatchlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchlistApplication.class, args);
	}

}
