package com.anandakshay.journalApp.controller;


import com.anandakshay.journalApp.entity.User;
import com.anandakshay.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userService.getUserByUsername(username);
        if(null!=userInDB){
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userService.saveNewEntry(userInDB);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
