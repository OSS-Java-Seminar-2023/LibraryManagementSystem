package org.oss.LibraryManagementSystem;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.oss.LibraryManagementSystem.models.enums.BookStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);

//		Encrypt password to store to db for each user

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String raw = "user";
		String encodedPassword = encoder.encode(raw);
//		System.out.println(encodedPassword);

		var faker = new Faker();
//		Generate authors
		for(int i = 0; i < 10; i++) {
			UUID uuid = UUID.randomUUID();
			var firstName = faker.name().firstName();
			var lastName = faker.name().lastName();

//			System.out.println(uuid);
//			System.out.println(firstName);
//			System.out.println(lastName);
//			System.out.println();
		}

//		Generate Categories
		for(int i = 0; i < 10; i++) {
			UUID uuid = UUID.randomUUID();
			var category = faker.book().genre();

//			System.out.println(uuid);
//			System.out.println(category);
//			System.out.println();
		}

//		Generate book info
		for(int i = 0; i < 20; i++) {
			UUID uuid = UUID.randomUUID();
			Random rand = new Random();
			int randomInt1 = rand.nextInt(10) + 1;
			int randomInt2 = rand.nextInt(10) + 1;
			var title = faker.book().title();
			var description = faker.lorem().sentence();

//			System.out.println(uuid);
//			System.out.println(title);
//			System.out.println(description);
//			System.out.println(randomInt1);
//			System.out.println(randomInt2);
//			System.out.println();
		}

//		Generate books
		for(int i = 0; i < 1; i++) {
			UUID uuid = UUID.randomUUID();
			Random rand = new Random();
			int randomInt1 = rand.nextInt(20) + 1;

			var publisherName = faker.book().publisher();
			var yearOfPublishing = Timestamp.from(Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt()));
			var isbn = faker.code().isbn13(true);
			var bookStatus = BookStatus.OK;
			var available = true;

//			Generate images
//			String API_URL = "https://source.unsplash.com/random/?nature";
//			HttpRequest request = HttpRequest.newBuilder()
//					.uri(URI.create(API_URL))
//					.method("GET", HttpRequest.BodyPublishers.noBody())
//					.build();
//
//			HttpResponse<String> response = null;
//
//			try {
//				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//			System.out.println(response);
////			JSONObject data = new JSONObject(response.body().toString());
////
////			JSONObject urls = new JSONObject(data.get("urls").toString());
////			System.out.println(urls.get("regular"));
//			System.out.println(uuid);
//			System.out.println(randomInt1);
//			System.out.println(publisherName);
//			System.out.println(yearOfPublishing);
//			System.out.println(isbn);
//			System.out.println(bookStatus);
//			System.out.println(available);
//			System.out.println();
		}


	}
}
