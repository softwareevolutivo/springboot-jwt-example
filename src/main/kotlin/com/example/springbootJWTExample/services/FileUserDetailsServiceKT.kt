package com.example.springbootJWTExample.services

import jakarta.annotation.PostConstruct
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class FileUserDetailsServiceKT : UserDetailsService {
    private val users: MutableMap<String, String> = HashMap()

    companion object {
        @JvmStatic
        private val USERS_FILE_PATH: String = "config/users.txt" // Ajusta la ruta según necesites
    }


    @PostConstruct
    fun init() {
//        try {
//            // Lee el archivo de usuarios al iniciar
//            Path path = Paths.get(USERS_FILE_PATH);
//            if (Files.exists(path)) {
//                Files.lines(path).forEach(line -> {
//                    String[] credentials = line.trim().split(":");
//                    if (credentials.length == 2) {
//                        users.put(credentials[0], credentials[1].trim());
//                    }
//                });
//                System.out.println("Usuarios cargados exitosamente del archivo");
//            } else {
//                System.out.println("Archivo de usuarios no encontrado en: " + USERS_FILE_PATH);
//            }
//        } catch (IOException e) {
//            System.out.println("Error al cargar usuarios desde archivo: " + e);
//            throw new RuntimeException("Error al inicializar usuarios", e);
//        }
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
//        String password = users.get(username);
//        if (password == null) {
//            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
//        }
//
        return User.builder()
            .username(username)
            .password("password")
            .authorities(emptyList()) // Sin roles/autoridades
            .build()
    }

    // Método útil para recargar usuarios del archivo
    fun reloadUsers() {
        users.clear()
        init()
    }

}