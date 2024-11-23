package com.example.springboot_jwt_example.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test")
    public fun hello() = "{'auth':'ok'}"
}