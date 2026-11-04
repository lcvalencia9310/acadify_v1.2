package com.example.acadify.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FirebaseAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // El preflight de CORS no envía Authorization; lo maneja CorsFilter antes que este filtro.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            rechazar(response, "Falta el token de autenticación");
            return;
        }

        String token = authHeader.substring(7);
        try {
            var decoded = FirebaseAuth.getInstance().verifyIdToken(token);
            request.setAttribute("uid", decoded.getUid());
            chain.doFilter(request, response);
        } catch (FirebaseAuthException e) {
            // Detalle solo en los logs del servidor: al cliente nunca se le expone el motivo exacto.
            log.warn("Token de Firebase rechazado. codigo={} mensaje={}", e.getAuthErrorCode(), e.getMessage());
            rechazar(response, "Token inválido o expirado");
        }
    }

    private void rechazar(HttpServletResponse response, String mensaje) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + mensaje + "\"}");
    }
}
