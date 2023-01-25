package com.example.librarysystem.LibrarySystem.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarysystem.LibrarySystem.models.Book;
import com.example.librarysystem.LibrarySystem.models.User;
import com.example.librarysystem.LibrarySystem.repositories.UserRepository;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    @Autowired
    private UserRepository userRep;

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<User> addNewUser(@RequestParam String name){
        // Create a new user and set his name
        User user = new User();
        user.setName(name);

        // Save in DB and return created user
        userRep.save(user);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "getbooks", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Book>> getBooks(@RequestParam Integer id){
        // Finds user by id, and fetches user's books
        List<Book> output = new ArrayList<Book>(userRep.findById(id).getBooks());
        return ResponseEntity.ok(output);
    }

    @RequestMapping(path = "getusers", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userRep.findAll());
    }
}
