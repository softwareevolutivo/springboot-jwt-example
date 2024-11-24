package com.example.springboot_jwt_example

import com.example.springboot_jwt_example.services.FileUserDetailsService
import com.example.springboot_jwt_example.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


class JwtRequestFilter : OncePerRequestFilter() {
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private val userDetailsService: FileUserDetailsService? = null

    init {
        jwtUtil = JwtUtil()
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val requestURI = request.requestURI
        println("Request URI: ${request.requestURI}")

        if (requestURI == "/authenticate") {
            chain.doFilter(request, response)
            return
        }

        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Obtener token sin el prefijo "Bearer "
                val jwt = authorizationHeader.substring(7)
                println("Token a validar: $jwt")
                val username = jwtUtil.extractUsername(jwt)

                // Si hay un username y no hay autenticación previa
                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService?.loadUserByUsername(username)

                    // Aquí es donde se usa validateToken
                    if (jwtUtil.validateToken(jwt)) {
                        val authToken = UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails?.authorities
                        )
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    } else println("Fallo............")
                }
            } catch (e: Exception) {
                logger.error("No se pudo validar el token: ${e.message}")
            }
        }

        chain.doFilter(request, response)
    }
}