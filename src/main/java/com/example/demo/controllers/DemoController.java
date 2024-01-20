package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @GetMapping("/home")
    public ResponseEntity<String> getHome(){
        System.out.println("GET: /home invoked");
        return new ResponseEntity<>("GET: /home invoked", HttpStatus.OK);
    }

    @PostMapping("/home")
    public ResponseEntity<String> postHome(){
        String message = "POST: /home invoked";
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/home")
    public ResponseEntity<String> deleteHome(){
        String message = "DELETE: /home invoked";
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/home")
    public ResponseEntity<String> putHome(){
        String message = "PUT: /home invoked";
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    // GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH, TRACE
    @PatchMapping("/home")
    public ResponseEntity<String> patchHome(){
        String message = "PATCH: /home invoked";
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/home", method= RequestMethod.HEAD)
    public ResponseEntity<String> headHome(){
        String message = "HEAD: /home invoked";
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK); // any respone will be discarded.
    }

    @RequestMapping(value = "/home", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsHome(){
        String message = "Options: /home invoked";
        System.out.println(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
