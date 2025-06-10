package com.rds.mywebapp.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyWebApi {

    @GetMapping
    public String hello() {
        return "Hello, The UpGrad DevOps learning is very interesting!";
    }

}
