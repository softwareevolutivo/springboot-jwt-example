package com.example.springbootJWTExample.controllers

import com.example.springbootJWTExample.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthControllerSE {

    private lateinit var jwtUtil: JwtUtil

    init {
        jwtUtil = JwtUtil()
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authRequest: AuthRequest): ResponseEntity<*> {
        println("Entró a AuthController")
        // Aquí debes validar las credenciales del usuario.
        // Por simplicidad, asumiremos que cualquier usuario es válido.

        println(authRequest.username)

        val jwt = jwtUtil.generateToken(authRequest.username)

        return ResponseEntity.ok(AuthResponse(jwt,authRequest.username))
    }
}

data class AuthRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val jwt: String,
    val username: String
)
