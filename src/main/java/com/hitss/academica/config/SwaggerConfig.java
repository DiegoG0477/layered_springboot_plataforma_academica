package com.hitss.academica.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server; // <-- Importar Server
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    // Bean para configuración general, sin perfil
    @Bean
    public OpenAPI baseOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("API Académica")
                        .version("1.0.0")
                        .description("Documentación de la API para el sistema de gestión académica."))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    // Bean ADICIONAL específico para producción
    // Spring combinará este bean con el de arriba cuando el perfil 'prod' esté activo.
    @Bean
    @Profile("prod")
    public OpenAPI prodOpenAPI(OpenAPI baseOpenAPI) {
        // Añadimos la URL del servidor de producción
        Server prodServer = new Server();
        prodServer.setUrl("https://dev-acad.parachico.xyz"); // <-- TU DOMINIO REAL
        prodServer.setDescription("Servidor de Producción");

        baseOpenAPI.addServersItem(prodServer);
        return baseOpenAPI;
    }

    // Bean ADICIONAL específico para desarrollo
    @Bean
    @Profile("dev")
    public OpenAPI devOpenAPI(OpenAPI baseOpenAPI) {
        // Añadimos la URL del servidor de desarrollo
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Servidor de Desarrollo Local");

        baseOpenAPI.addServersItem(devServer);
        return baseOpenAPI;
    }
}