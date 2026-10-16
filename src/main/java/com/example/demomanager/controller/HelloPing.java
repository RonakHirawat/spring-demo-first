package com.example.demomanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloPing {
    @GetMapping("/ping")
    public String ping(){
        return "Pong";
    }
    @GetMapping("/status")
    public Greeting greeting(){
        return new Greeting( "Hi there", 7);
    }
}


