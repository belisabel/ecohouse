package com.EcoHouse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI para documentación de la API EcoHouse.
 *
 * Acceso a la documentación:
 * - Swagger UI: http://localhost:8081/swagger-ui/index.html
 * - OpenAPI JSON: http://localhost:8081/v3/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ecoHouseOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com")
                                .description("Servidor AWS Elastic Beanstalk (Producción)"),
                        new Server()
                                .url("http://localhost:5000")
                                .description("Servidor de desarrollo local")
                ))
                .components(new Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Autenticación básica HTTP")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }

    private Info apiInfo() {
        return new Info()
                .title("EcoHouse API - Environmental Impact Reports")
                .description("""
                        API REST para gestión de reportes de impacto ambiental de EcoHouse.
                        
                        **Funcionalidades principales:**
                        - 🌱 Generación de reportes de impacto ambiental
                        - 📊 Métricas de CO2 ahorrado y huella de carbono
                        - 🏆 Sistema de puntos ecológicos (Eco Points)
                        - 📈 Análisis de tendencias mensuales
                        - 🎯 Seguimiento de productos sostenibles
                        - 🔍 Estadísticas agregadas por cliente
                        
                        **Endpoints disponibles:**
                        - `/api/reports/*` - Gestión de reportes de impacto
                        - `/auth/*` - Autenticación y registro
                        - `/user/*` - Gestión de usuarios
                        
                        **Nota:** Los endpoints `/api/**` están abiertos para pruebas (sin autenticación requerida).
                        """)
                .version("1.0.0")
                .contact(apiContact())
                .license(apiLicense());
    }

    private Contact apiContact() {
        return new Contact()
                .name("Equipo EcoHouse")
                .email("soporte@ecohouse.com")
                .url("https://www.ecohouse.com");
    }

    private License apiLicense() {
        return new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");
    }
}

