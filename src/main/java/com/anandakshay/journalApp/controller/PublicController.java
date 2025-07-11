package com.anandakshay.journalApp.controller;


import com.anandakshay.journalApp.entity.User;
import com.anandakshay.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    ResponseEntity<?> saveUser(@RequestBody User user){
        userService.saveNewEntry(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
