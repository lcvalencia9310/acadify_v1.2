package com.example.acadify.config;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Verifica FirebaseAuthFilter de forma aislada (sin levantar el contexto de Spring,
 * sin base de datos y sin un proyecto real de Firebase): usa unas credenciales
 * de relleno solo para poder inicializar FirebaseApp, que es lo que exige la
 * librería para poder invocar FirebaseAuth.getInstance().verifyIdToken(...).
 */
class FirebaseAuthFilterTest {

    @BeforeAll
    static void inicializarFirebaseDePrueba() {
        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }
        GoogleCredentials credencialesDeRelleno = GoogleCredentials.create(new AccessToken("token-de-relleno", null));
        FirebaseApp.initializeApp(FirebaseOptions.builder()
                .setCredentials(credencialesDeRelleno)
                .setProjectId("acadify-test")
                .build());
    }

    private final FirebaseAuthFilter filtro = new FirebaseAuthFilter();

    @Test
    void rechazaCuandoFaltaElHeaderAuthorization() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/syllabus");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filtro.doFilter(request, response, chain);

        assertEquals(401, response.getStatus());
        assertEquals("application/json", response.getContentType());
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    void rechazaCuandoElTokenNoEsValido() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/syllabus");
        request.addHeader("Authorization", "Bearer token-invalido-de-prueba");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filtro.doFilter(request, response, chain);

        assertEquals(401, response.getStatus());
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    void dejaPasarElPreflightOptionsSinExigirToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/syllabus");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filtro.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(any(), any());
    }
}
