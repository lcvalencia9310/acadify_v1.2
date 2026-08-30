package com.example.acadify.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FirebaseConfig {

    @PostConstruct
    public void inicializar() {
        try {
            InputStream credenciales = obtenerCredenciales();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credenciales))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar Firebase", e);
        }
    }

    private InputStream obtenerCredenciales() throws IOException {
        // En Render: lee el JSON completo desde una variable de entorno
        String credencialesEnv = System.getenv("FIREBASE_CREDENTIALS_JSON");
        if (credencialesEnv != null && !credencialesEnv.isBlank()) {
            return new ByteArrayInputStream(credencialesEnv.getBytes(StandardCharsets.UTF_8));
        }
        // En tu máquina local: sigue leyendo el archivo, como hasta ahora
        return new ClassPathResource("firebase-service-account.json").getInputStream();
    }
}
