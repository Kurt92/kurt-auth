package com.kurt.kurt_auth;

import com.kurt.kurt_auth.framework.util.AwsParameterStore.AwsParameterStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class KurtAuthApplication {

	public static void main(String[] args) {

		// .env 파일 로드
//		Dotenv dotenv = Dotenv.configure().load();
//		System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));

		// aws 파라미터 스토어
//		String jwtSecretKey = AwsParameterStore.getParameter("/myapp/JWT_SECRET_KEY");
//		System.setProperty("JWT_SECRET_KEY", jwtSecretKey);

		SpringApplication.run(KurtAuthApplication.class, args);
	}

}
