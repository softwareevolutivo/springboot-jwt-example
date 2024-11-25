package com.example.springbootJWTExample.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestControllerKT {
    @GetMapping("/test")
    fun hello(): Map<String, String> = mapOf("status" to "ok")
}