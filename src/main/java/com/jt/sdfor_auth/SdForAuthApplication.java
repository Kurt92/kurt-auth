package com.jt.sdfor_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SdForAuthApplication {

	public static void main(String[] args) {

		// .env 파일 로드
//		Dotenv dotenv = Dotenv.configure().load();
//		System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));

		SpringApplication.run(SdForAuthApplication.class, args);
	}

}
