package com.hitss.academica.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitss.academica.dto.auth.LoginRequestDTO;
import com.hitss.academica.dto.auth.LoginResponseDTO;
import com.hitss.academica.security.TokenJwtConfig;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenJwtConfig tokenJwtConfig;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenJwtConfig tokenJwtConfig) {
        // Pasamos el AuthenticationManager al constructor de la clase padre
        super(authenticationManager);
        this.tokenJwtConfig = tokenJwtConfig;
        // Definimos explícitamente que este filtro SOLO debe actuar sobre POST /api/auth/login
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequestDTO loginRequest;
        try {
            loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
        } catch (IOException e) {
            logger.error("Error al deserializar LoginRequestDTO", e);
            throw new RuntimeException("Error en el formato de los datos de autenticación", e);
        }

        String username = loginRequest.getEmail(); // El "username" para Spring Security es nuestro email
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        // Usamos getAuthenticationManager() que fue establecido en el constructor de la clase padre
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String email = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        String token = Jwts.builder()
                .subject(email)
                .claim("authorities", new ObjectMapper().writeValueAsString(roles))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenJwtConfig.EXPIRATION_TIME))
                .signWith(tokenJwtConfig.SECRET_KEY)
                .compact();

        LoginResponseDTO loginResponse = new LoginResponseDTO(token, tokenJwtConfig.EXPIRATION_TIME / 1000);

        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponse));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error de autenticación: email o contraseña incorrectos.");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
    }
}