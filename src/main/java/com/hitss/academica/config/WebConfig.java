package com.hitss.academica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class WebConfig {

    /**
     * Este filtro es esencial cuando la aplicación corre detrás de un proxy inverso (como Nginx).
     * Lee las cabeceras X-Forwarded-* (Host, Proto, Port) para que la aplicación
     * sepa la URL original que el cliente solicitó. Esto es crucial para CORS,
     * redirects, y la generación de URLs correctas en Swagger y HATEOAS.
     */
    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
}