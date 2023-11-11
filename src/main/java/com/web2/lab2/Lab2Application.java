package com.web2.lab2;

import com.web2.lab2.model.Comment;
import com.web2.lab2.model.UserTable;
import com.web2.lab2.repository.CommentRepository;
import com.web2.lab2.repository.UsertableRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab2Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab2Application.class, args);
	}

	//Add default user
	@Bean
	public CommandLineRunner demo(UsertableRepository usertableRepository, CommentRepository commentRepository) {
		return args -> {
			UserTable user = new UserTable();
			user.setUsername("Mario");

			String password = DigestUtils.sha512Hex("lozinka").substring(0, 50);
			user.setPassword(password);

			usertableRepository.save(user);

			Comment comment = new Comment();
			comment.setUsername("Slavko Haker");
			comment.setContent("<img src=x onerror=\"javascript:alert('XSS-Slavko_Haker_Comment')\">");

			commentRepository.save(comment);

			Comment comment2 = new Comment();
			comment2.setUsername("Ime Prezime");
			comment2.setContent("Dobar dan!");
			commentRepository.save(comment2);
		};
	}
}
