package com.devstan.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("user")
    public String getUser() {
        return "Hello";
    }

    @GetMapping("product")
    public String getProduct() {
        return "product interface";
    }
}
