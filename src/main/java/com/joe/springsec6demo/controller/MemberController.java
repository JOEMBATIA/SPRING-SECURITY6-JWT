package com.joe.springsec6demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
public class MemberController {

    @GetMapping
    public String memberAccess(){
        return "SECURE ENDPOINT :: GET -> Member";
    }

    @PostMapping
    public String memberCreate(){
        return "SECURE ENDPOINT :: POST -> Member";
    }

    @PutMapping
    public String memberUpdate(){
        return "SECURE ENDPOINT :: UPDATE -> Member";
    }

    @DeleteMapping
    public String memberDelete(){
        return "SECURE ENDPOINT :: DELETE -> Member";
    }
}
