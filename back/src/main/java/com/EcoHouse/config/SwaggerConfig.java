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
 * Configuración de Swagger/OpenAPI para documentación completa de la API EcoHouse.
 *
 * EcoHouse es una plataforma integral de e-commerce sostenible que combina
 * funcionalidades de tienda online con seguimiento de impacto ambiental.
 *
 * Acceso a la documentación:
 * - Swagger UI: http://localhost:9000/swagger-ui/index.html
 * - OpenAPI JSON: http://localhost:9000/v3/api-docs
 *
 * Esta configuración define la información general de la API, servidores disponibles,
 * y esquemas de seguridad para la documentación interactiva.
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
                                .url("http://localhost:9000")
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
                .title("EcoHouse API - E-Commerce Sostenible")
                .description("""
                        **API REST completa para la plataforma de comercio electrónico sostenible EcoHouse.**
                        
                        EcoHouse es una solución integral de e-commerce enfocada en productos ecológicos y sostenibles,
                        que combina funcionalidades de tienda online con seguimiento de impacto ambiental.
                        
                        ## 🌿 Módulos Principales
                        
                        ### 🛍️ E-Commerce Core
                        - **Productos**: Catálogo completo con datos ambientales (huella de carbono, materiales, certificaciones)
                        - **Categorías y Marcas**: Organización de productos ecológicos
                        - **Carrito de Compras**: Gestión de items con cálculo de impacto ambiental en tiempo real
                        - **Órdenes**: Sistema completo de pedidos con seguimiento de estados (paginación, filtros)
                        - **Pagos**: Procesamiento simplificado de pagos
                        
                        ### 📊 Reportes y Análisis
                        - **Reportes de Impacto Ambiental**: Análisis detallado del impacto ecológico por cliente
                        - **Estadísticas de Ventas**: Métricas de negocio (ventas totales, promedio, por cliente)
                        - **CO2 Ahorrado**: Seguimiento de reducción de huella de carbono
                        - **Eco Points**: Sistema de puntos de recompensa por compras sostenibles
                        
                        ### 👥 Gestión de Usuarios
                        - **Autenticación**: Sistema seguro de login y registro
                        - **Clientes (Customers)**: Perfiles con historial de compras y seguimiento ambiental
                        - **Administración**: Gestión de usuarios y permisos
                        
                        ## 📍 Endpoints Disponibles
                        
                        | Módulo | Ruta Base | Descripción |
                        |--------|-----------|-------------|
                        | **Productos** | `/api/products` | CRUD de productos, búsqueda, filtros |
                        | **Categorías** | `/api/categories` | Gestión de categorías |
                        | **Marcas** | `/api/brands` | Gestión de marcas |
                        | **Carrito** | `/api/cart` | Operaciones del carrito de compras |
                        | **Órdenes** | `/api/orders` | Gestión de pedidos (con paginación) |
                        | **Pagos** | `/api/payments` | Procesamiento de pagos |
                        | **Ventas** | `/api/sales` | Reportes y estadísticas de ventas |
                        | **Reportes** | `/api/reports` | Reportes de impacto ambiental |
                        | **Usuarios** | `/api/users` | Gestión de clientes |
                        | **Autenticación** | `/auth` | Login, registro, validación |
                        | **Admin** | `/api/admin` | Funciones administrativas |
                        
                        ## 🔑 Características Clave
                        
                        ✅ Sistema de paginación en listados
                        ✅ Cálculo automático de impacto ambiental
                        ✅ Datos ambientales por producto (huella de carbono, materiales, certificaciones)
                        ✅ Sistema de certificaciones ecológicas (GOTS, OEKO-TEX, Fair Trade, etc.)
                        ✅ Seguimiento de CO2 ahorrado en cada compra
                        ✅ Puntos de recompensa ecológicos
                        ✅ Reportes personalizados por cliente
                        ✅ Estadísticas de ventas en tiempo real
                        ✅ API RESTful con documentación completa
                        
                        ## 💡 Uso
                        
                        La mayoría de endpoints están abiertos para pruebas (sin autenticación).
                        Para operaciones sensibles, se requiere autenticación básica HTTP.
                        
                        ## 📖 Documentación Adicional
                        
                        - **OpenAPI JSON**: `/v3/api-docs`
                        - **Swagger UI**: `/swagger-ui/index.html`
                        
                        ---
                        
                        **Versión de API**: 1.0.0 | **Última actualización**: Diciembre 2025
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

