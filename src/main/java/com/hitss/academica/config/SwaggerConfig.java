package com.hitss.academica.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    // Inyectamos el valor del perfil activo. Si no se define, por defecto será "dev".
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    // Inyectamos la URL del frontend para la configuración de CORS en dev.
    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        // Creamos la base de la configuración de OpenAPI
        OpenAPI openAPI = new OpenAPI()
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

        // ---- LÓGICA CONDICIONAL BASADA EN EL PERFIL ----
        if ("prod".equals(activeProfile)) {
            // Si estamos en producción, añadimos el servidor de producción
            openAPI.servers(List.of(
                new Server().url("https://dev-acad.parachico.xyz").description("Servidor de Pruebas")
            ));
        } else {
            // Para cualquier otro perfil (dev), añadimos el servidor local
            openAPI.servers(List.of(
                new Server().url("http://localhost:8080").description("Servidor de Desarrollo Local")
            ));
        }

        return openAPI;
    }
}