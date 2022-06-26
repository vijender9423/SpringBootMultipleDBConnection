package com.shl.scoring.amcat.controller;

import com.shl.scoring.amcat.repositories.tc.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/addUser")
    public ResponseEntity addUser(@RequestBody String user){
        System.out.println("recieved request");
        return new ResponseEntity("true", HttpStatus.OK);
    }
}
