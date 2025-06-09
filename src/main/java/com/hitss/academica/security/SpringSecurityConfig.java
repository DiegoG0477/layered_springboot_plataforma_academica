package com.hitss.academica.security;

import com.hitss.academica.security.filter.JwtAuthenticationFilter;
import com.hitss.academica.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

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
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // <-- AÑADIDO: Habilitar CORS
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register").permitAll()
                .requestMatchers(
                    "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                    "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
                    "/configuration/security", "/webjars/**"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/notas/estudiante/{id}").hasRole("ESTUDIANTE")
                .requestMatchers(HttpMethod.GET, "/api/materiales/asignatura/{id}").hasRole("ESTUDIANTE")
                .requestMatchers(HttpMethod.GET, "/api/reportes/historial-estudiante/{id}").hasRole("ESTUDIANTE")
                .requestMatchers(HttpMethod.GET, "/api/profesores/{id}/asignaturas").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.GET, "/api/notas/asignatura/{id}").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.GET, "/api/reportes/notas-promedio").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.POST, "/api/notas/**", "/api/materiales/**").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.PUT, "/api/notas/**").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.DELETE, "/api/notas/**", "/api/materiales/**").hasRole("PROFESOR")
                .requestMatchers("/api/usuarios/**", "/api/roles/**", "/api/profesores/**",
                                 "/api/estudiantes/**", "/api/cursos/**", "/api/periodos/**",
                                 "/api/asignaturas/**", "/api/reportes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()
                .anyRequest().denyAll()
            )
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenJwtConfig))
            .addFilterBefore(new JwtValidationFilter(authenticationManager(), tokenJwtConfig), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://127.0.0.1:5173"));

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}