package com.example.kakaocoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class KakaoCouponApplication {
	public static void main(String[] args) {
		SpringApplication.run(KakaoCouponApplication.class, args);
	}
}
