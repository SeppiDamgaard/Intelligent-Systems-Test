package com.example.librarysystem.LibrarySystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.librarysystem.LibrarySystem.controllers.BookController;
import com.example.librarysystem.LibrarySystem.controllers.UserController;
import com.example.librarysystem.LibrarySystem.models.User;
import com.example.librarysystem.LibrarySystem.repositories.UserRepository;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@SpringBootTest
class  LibrarySystemApplicationTests {

	@Resource
	private BookController bookCon;
	@Resource
	private UserController userCon;
	@Resource
	private UserRepository userRep;

	@Test
	@Transactional
	void contextLoads() {
		// Checks if user can be created
		User user = userCon.addNewUser("Test user").getBody();
		assertNotNull(user);

		// Checks if book can be rented
		bookCon.borrowBook(user.getId(), "9781501182099");
		assertEquals(user.getBooks().size(), 1, "Book not borrowed");

		// Checks if book can be returned
		bookCon.returnBook(user.getId(), "9781501182099");
		assertEquals(user.getBooks().size(), 0, "Book not returned");

		// Deletes test user
		userRep.delete(user);
	}

}
