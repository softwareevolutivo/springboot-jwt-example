package com.example.springbootJWTExample

import com.example.springbootJWTExample.filters.JwtRequestFilterKT
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = ["com.example.springbootJWTExample"])
class SecurityConfigKT {
    @Bean
    @Throws(Exception::class)
        fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/authenticate").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }


//     Filtro personalizado para JWT
        @Bean
        fun jwtRequestFilter() : JwtRequestFilterKT {
            return JwtRequestFilterKT();
        }

}