package com.hitss.academica.security;

import com.hitss.academica.security.filter.JwtAuthenticationFilter;
import com.hitss.academica.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private TokenJwtConfig tokenJwtConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        String hierarchy = "ROLE_ADMIN > ROLE_PROFESOR \n ROLE_PROFESOR > ROLE_ESTUDIANTE";
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
    }

    @Bean
    public static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), tokenJwtConfig);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // 1. Endpoints Públicos
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/webjars/**"
                ).permitAll()

                // 2. Endpoints de ESTUDIANTE
                .requestMatchers(HttpMethod.GET, "/api/notas/estudiante/{id}").hasRole("ESTUDIANTE")
                .requestMatchers(HttpMethod.GET, "/api/materiales/asignatura/{id}").hasRole("ESTUDIANTE")

                // 3. Endpoints de PROFESOR (ADMIN hereda estos permisos)
                .requestMatchers(HttpMethod.GET, "/api/profesores/{id}/asignaturas").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.GET, "/api/notas/asignatura/{id}").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.POST, "/api/notas/**", "/api/materiales/**").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.PUT, "/api/notas/**").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.DELETE, "/api/notas/**", "/api/materiales/**").hasRole("PROFESOR")

                // 4. Endpoints de ADMIN
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers("/api/reportes/**").hasRole("ADMIN")
                .requestMatchers("/api/roles/**").hasRole("ADMIN")
                .requestMatchers("/api/profesores/**").hasRole("ADMIN")
                .requestMatchers("/api/estudiantes/**").hasRole("ADMIN")
                .requestMatchers("/api/cursos/**").hasRole("ADMIN")
                .requestMatchers("/api/periodos/**").hasRole("ADMIN")
                .requestMatchers("/api/asignaturas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/reportes/reporte-final/{cursoId}").hasRole("ADMIN")

                // 5. Endpoints Autenticados
                .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

                // 6. Denegar todo lo demás
                .anyRequest().denyAll()
            )
            .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtValidationFilter(authenticationManager(), tokenJwtConfig), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}