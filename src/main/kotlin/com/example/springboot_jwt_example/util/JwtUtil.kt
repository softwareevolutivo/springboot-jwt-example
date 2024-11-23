package com.example.springboot_jwt_example.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component;
import java.security.Key
import java.util.Date;
import javax.crypto.SecretKey

class JwtUtil {
    private val SECRET_KEY = "C83LPw1h18xhZ1fbVNqkVSWpKqOTF6S9npCG4wrBM80"

    private val key: SecretKey = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(Charsets.UTF_8))

    fun generateToken(username: String): String {
        val token = Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
            .signWith(key)  // Especificamos el algoritmo
            .compact()

        // Intentar validar inmediatamente
        try {
            val claims = getClaims(token)

            println("""
                Validación inmediata exitosa:
                Subject: ${claims?.payload?.subject}
                IssuedAt: ${claims?.payload?.issuedAt}
                Expiration: ${claims?.payload?.expiration}
            """.trimIndent())
        } catch (e: Exception) {
            println("Error en validación inmediata: ${e.message}")
            e.printStackTrace()
        }

        println("Token generado: $token")
        return token
    }

    private fun getClaims(token: String): Jws<Claims>? {
        return try {
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
            claims
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            null
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)

            // Imprimir los claims para debug
            println("""
                Token validado correctamente:
                Subject: ${claims?.payload?.subject}
                IssuedAt: ${claims?.payload?.issuedAt}
                Expiration: ${claims?.payload?.expiration}
            """.trimIndent())

            // Verificar que no ha expirado
            !claims?.payload?.expiration?.before(Date())!!
        } catch (e: Exception) {
            println("Error validando token: ${e.message}")
            false
        }
    }

    fun extractUsername(token: String): String? {
        return try {
            getClaims(token)?.payload?.subject
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            null
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getClaims(token)?.payload?.expiration
        return expiration?.before(Date()) ?: true
    }
}
