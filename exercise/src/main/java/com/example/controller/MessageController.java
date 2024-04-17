package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("~/api/messages")
public class MessageController {
     @GetMapping("/hello")
     public String hello() {
        return "this is a test";
     }
}
