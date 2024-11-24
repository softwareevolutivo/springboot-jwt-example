package com.example.springboot_jwt_example.services;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUserDetailsService implements UserDetailsService {
    private Map<String, String> users = new HashMap<>();
    private static final String USERS_FILE_PATH = "config/users.txt";  // Ajusta la ruta según necesites

    @PostConstruct
    public void init() {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String password = users.get(username);
//        if (password == null) {
//            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
//        }
//
        return User.builder()
                .username(username)
                .password("password")
                .authorities(Collections.emptyList()) // Sin roles/autoridades
                .build();
    }

    // Método útil para recargar usuarios del archivo
    public void reloadUsers() {
        users.clear();
        init();
    }
}
