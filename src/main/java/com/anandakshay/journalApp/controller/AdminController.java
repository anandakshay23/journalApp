package com.anandakshay.journalApp.controller;


import com.anandakshay.journalApp.entity.User;
import com.anandakshay.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser(){
        List<User> list = userService.getUserEntries();
        if(null!=list && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/give-admin-access")
    public ResponseEntity<?> giveAdminAccess(@RequestBody User user){
        user.getRoles().add("ADMIN");
        userService.saveNewEntry(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
