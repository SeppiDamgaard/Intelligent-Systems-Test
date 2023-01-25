package com.example.librarysystem.LibrarySystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarysystem.LibrarySystem.models.Book;
import com.example.librarysystem.LibrarySystem.models.User;
import com.example.librarysystem.LibrarySystem.repositories.BookRepository;
import com.example.librarysystem.LibrarySystem.repositories.UserRepository;

@RestController
@RequestMapping(path = "/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRep;
    @Autowired
    private UserRepository userRep;

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Book> addNewBook (@RequestParam String title, @RequestParam String author, @RequestParam String isbn, @RequestParam Integer stock){
        // Create a new user and sets properties
        Book book = new Book(title, author, isbn, stock);

        // Save in DB and return created book
        bookRep.save(book);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(path = "/getavailable", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Book>> getAvailableBooks (){
        List<Book> foundBooks = bookRep.findByAtLeast("stock", 1);
        return ResponseEntity.ok(foundBooks);
    }

    @RequestMapping(path = "borrow", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Book> borrowBook(@RequestParam Integer userId, @RequestParam String isbn) {
        // Tries to find the user and the book
        User user = userRep.findById(userId);
        Book book = (Book) bookRep.findByAttributeContainsText("isbn", isbn).get(0);
        
        // TODO: Improve error responses
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(book);
        if (book == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if (book.getStock() <= 0)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(book);
        
        // Updates book
        book.getUsers().add(user);
        book.setStock(book.getStock() - 1);
        // Updates user
        user.getBooks().add(book);
        // Saves and returns found book
        bookRep.save(book);
        userRep.save(user);

        return ResponseEntity.ok(book);
    }
    
    @RequestMapping(path = "return", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Book> returnBook(@RequestParam Integer userId, @RequestParam String isbn) {
        // Finds books based on isbn nr.
        Book foundBook = bookRep.findByAttributeContainsText("isbn", isbn).get(0);
        User user = userRep.findById(userId);

        // Removes the reservation from user
        user.getBooks().remove(foundBook);

        userRep.save(user);
        foundBook.setStock(foundBook.getStock() + 1);
        bookRep.save(foundBook);
        return ResponseEntity.ok(foundBook);
    }
}
