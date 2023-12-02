package org.oss.LibraryManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String raw = "user";
		String encodedPassword = encoder.encode(raw);
		System.out.println(encodedPassword);
	}
}
