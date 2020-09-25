package com.jason.springbootjpa.controller;

import com.jason.springbootjpa.controller.response.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(name = "/")
    public ResponseEntity<Object> test(){
        return ResponseEntity.ok(new Response("Hello"));
    }
}
