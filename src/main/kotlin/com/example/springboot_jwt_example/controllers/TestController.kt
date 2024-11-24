package com.example.springboot_jwt_example.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test")
    fun hello(): Map<String, String> = mapOf("status" to "ok")
}