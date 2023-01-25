package com.example.librarysystem.LibrarySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.librarysystem.LibrarySystem.controllers.BookController;
import com.example.librarysystem.LibrarySystem.controllers.UserController;
import com.example.librarysystem.LibrarySystem.repositories.extensions.ExtRepImplementation;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ExtRepImplementation.class, BookController.class, UserController.class})
@EntityScan("com.example.librarysystem.LibrarySystem")
@EnableJpaRepositories(repositoryBaseClass = ExtRepImplementation.class)
@EnableAutoConfiguration
public class LibrarySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarySystemApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/api/***/***").allowedOrigins("http://localhost");
			}
		};
	}

}
