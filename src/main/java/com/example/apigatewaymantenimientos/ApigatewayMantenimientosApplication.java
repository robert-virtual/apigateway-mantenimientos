package com.example.apigatewaymantenimientos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApigatewayMantenimientosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayMantenimientosApplication.class, args);
	}

}
