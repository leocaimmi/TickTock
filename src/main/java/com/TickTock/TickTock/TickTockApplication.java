package com.TickTock.TickTock;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TickTockApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		// TODO: Document all methods with 401 and 403 response codes
		// JWT Configuration
		System.setProperty("APPLICATION_SECURITY_JWT_SECRET_KEY",
				dotenv.get("APPLICATION_SECURITY_JWT_SECRET_KEY"));
		System.setProperty("APPLICATION_SECURITY_JWT_EXPIRATION",
				dotenv.get("APPLICATION_SECURITY_JWT_EXPIRATION"));
		System.setProperty("APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION",
				dotenv.get("APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION"));


		System.setProperty("MAIL_HOST",
				dotenv.get("MAIL_HOST"));
		System.setProperty("MAIL_PORT",
				dotenv.get("MAIL_PORT"));
		System.setProperty("MAIL_PROTOCOL",
				dotenv.get("MAIL_PROTOCOL"));
		System.setProperty("MAIL_USERNAME",
				dotenv.get("MAIL_USERNAME"));
		System.setProperty("MAIL_PASSWORD",
				dotenv.get("MAIL_PASSWORD"));
		System.setProperty("MAIL_SMTP_AUTH",
				dotenv.get("MAIL_SMTP_AUTH"));
		System.setProperty("MAIL_STARTTLS_ENABLE",
				dotenv.get("MAIL_STARTTLS_ENABLE"));
		System.setProperty("MAIL_STARTTLS_REQUIRED",
				dotenv.get("MAIL_STARTTLS_REQUIRED"));
		System.setProperty("MAIL_SSL_TRUST",
				dotenv.get("MAIL_SSL_TRUST"));

		SpringApplication.run(TickTockApplication.class, args);
	}

	/*http://localhost:8080/swagger-ui/index.html |entrar a swagger
	http://localhost:8080/h2-console | entrar en H2 bbdd*/
}
