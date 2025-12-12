# 📚 EcoHouse Backend - Documentación Completa

> Sistema de E-commerce Sostenible con Tracking de Impacto Ambiental

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![AWS](https://img.shields.io/badge/AWS-Elastic%20Beanstalk-orange.svg)](https://aws.amazon.com/elasticbeanstalk/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📋 Tabla de Contenidos

1. [Descripción General](#-descripción-general)
2. [Características Principales](#-características-principales)
3. [Arquitectura del Sistema](#-arquitectura-del-sistema)
4. [Stack Tecnológico](#-stack-tecnológico)
5. [Estructura del Proyecto](#-estructura-del-proyecto)
6. [Modelo de Datos](#-modelo-de-datos)
7. [API Endpoints](#-api-endpoints)
8. [Configuración](#-configuración)
9. [Instalación y Ejecución](#-instalación-y-ejecución)
10. [Despliegue](#-despliegue)
11. [Testing](#-testing)
12. [Seguridad](#-seguridad)
13. [Sistema de EcoPoints](#-sistema-de-ecopoints)
14. [Contribución](#-contribución)

---

## 🌍 Descripción General

**EcoHouse** es una plataforma de e-commerce especializada en productos sostenibles y ecológicos. El backend proporciona una API RESTful completa que permite:

- **Gestión de productos ecológicos** con datos ambientales detallados
- **Tracking de huella de carbono** de cada compra
- **Sistema de gamificación** con EcoPoints basado en impacto ambiental real
- **Reportes de impacto ambiental** personalizados para cada usuario
- **Marketplace sostenible** con marcas certificadas

### 🎯 Propósito

Facilitar el comercio de productos sostenibles mientras educamos a los consumidores sobre su impacto ambiental, incentivando decisiones de compra más conscientes a través de gamificación y transparencia.

---

## ✨ Características Principales

### 🛍️ E-commerce Core
- ✅ Catálogo de productos con filtros avanzados
- ✅ Carrito de compras con persistencia
- ✅ Gestión de órdenes (CRUD completo)
- ✅ Sistema de pagos simplificado
- ✅ Múltiples roles de usuario (CUSTOMER, BRAND_ADMIN)

### 🌱 Sostenibilidad
- ✅ **Datos Ambientales por Producto:**
  - Huella de carbono (CO2)
  - Material y origen
  - Consumo energético
  - Porcentaje reciclable
  - Certificaciones ecológicas

- ✅ **Reportes de Impacto Ambiental:**
  - CO2 total generado y ahorrado
  - Eco Points ganados
  - Nivel de impacto (BAJO, MODERADO, BUENO, EXCELENTE)
  - Productos más sostenibles comprados
  - Tendencias mensuales

### 🎮 Gamificación
- ✅ **Sistema de EcoPoints Dinámico:**
  - Cálculo basado en impacto ambiental real
  - Fórmula: `(Base + CO2 + Productos + Gasto) × Multiplicador`
  - Niveles: Eco Novato → Eco Legend
  - Badges y achievements

### 📊 Reportes y Analytics
- ✅ Reportes de ventas con paginación
- ✅ Estadísticas por cliente
- ✅ Dashboard de impacto ambiental
- ✅ Tendencias de compra sostenible

---

## 🏗️ Arquitectura del Sistema

### Patrón de Arquitectura

```
┌─────────────────────────────────────────────────┐
│              Cliente (Frontend)                  │
│         React / Angular / Mobile App            │
└────────────────┬────────────────────────────────┘
                 │ HTTP/REST
                 ▼
┌─────────────────────────────────────────────────┐
│           API Gateway (Spring MVC)              │
│              Controllers Layer                   │
└────────────────┬────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────┐
│           Service Layer (Lógica de Negocio)     │
│  - OrderService                                 │
│  - ProductService                               │
│  - ImpactReportService                          │
│  - EcoPointsCalculationService                  │
│  - SalesReportService                           │
└────────────────┬────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────┐
│         Repository Layer (Spring Data JPA)      │
│              Hibernate ORM                       │
└────────────────┬────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────┐
│              Base de Datos                       │
│            MySQL 8.0 (AWS RDS)                  │
└─────────────────────────────────────────────────┘
```

### Componentes Principales

#### 1. **Controllers** (Capa de Presentación)
- `ProductController`: Gestión de productos
- `OrderController`: Gestión de órdenes
- `ShoppingCartController`: Carrito de compras
- `ImpactReportController`: Reportes ambientales
- `SalesReportController`: Reportes de ventas
- `AuthController`: Autenticación y autorización

#### 2. **Services** (Capa de Negocio)
- `OrderServiceImpl`: Lógica de órdenes
- `ProductServiceImpl`: Lógica de productos
- `ImpactReportServiceImpl`: Cálculo de impacto ambiental
- `EcoPointsCalculationService`: Cálculo de EcoPoints
- `SalesReportService`: Estadísticas de ventas

#### 3. **Repositories** (Capa de Datos)
- Spring Data JPA con métodos CRUD automáticos
- Queries personalizadas con JPQL
- Paginación y ordenamiento

#### 4. **Models** (Entidades)
- `Product`, `Category`, `Brand`
- `Order`, `OrderItem`, `Payment`
- `Customer`, `ShoppingCart`
- `ImpactReport`, `EnvironmentalData`

---

## 🛠️ Stack Tecnológico

### Backend Core
| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 21 (LTS) | Lenguaje principal |
| **Spring Boot** | 3.5.7 | Framework principal |
| **Spring Data JPA** | 3.5.7 | Persistencia de datos |
| **Spring Security** | 6.x | Autenticación y autorización |
| **Hibernate** | 6.x | ORM |

### Base de Datos
| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **MySQL** | 8.0+ | Base de datos principal |
| **AWS RDS** | MySQL 8.0 | Base de datos en producción |

### Documentación y Testing
| Tecnología | Propósito |
|------------|-----------|
| **Springdoc OpenAPI** | Documentación API (Swagger) |
| **JUnit 5** | Testing unitario |
| **Mockito** | Mocking para tests |

### Herramientas de Desarrollo
| Herramienta | Propósito |
|-------------|-----------|
| **Maven** | Gestión de dependencias |
| **Lombok** | Reducción de boilerplate |
| **Git** | Control de versiones |
| **IntelliJ IDEA** | IDE recomendado |

### DevOps y Despliegue
| Tecnología | Propósito |
|------------|-----------|
| **AWS Elastic Beanstalk** | Hosting y orquestación |
| **GitHub Actions** | CI/CD Pipeline |
| **Docker** | Containerización (opcional) |

---

## 📁 Estructura del Proyecto

```
back/
├── src/
│   ├── main/
│   │   ├── java/com/EcoHouse/
│   │   │   ├── EcoHouseApplication.java          # Punto de entrada
│   │   │   │
│   │   │   ├── auth/                             # 🔐 Autenticación
│   │   │   │   ├── controller/
│   │   │   │   └── service/
│   │   │   │
│   │   │   ├── category/                         # 📂 Categorías
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── services/
│   │   │   │
│   │   │   ├── config/                           # ⚙️ Configuración
│   │   │   │   ├── DataLoader.java              # Datos iniciales
│   │   │   │   ├── SecurityConfig.java          # Seguridad
│   │   │   │   └── SwaggerConfig.java           # Swagger
│   │   │   │
│   │   │   ├── impactReport/                     # 🌍 Reportes Ambientales
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── dtoRequest/
│   │   │   │   ├── dtoResponse/
│   │   │   │   ├── entities/
│   │   │   │   ├── Enum/
│   │   │   │   ├── mapper/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   │
│   │   │   ├── order/                            # 🛒 Órdenes
│   │   │   │   ├── controller/
│   │   │   │   │   ├── OrderController.java
│   │   │   │   │   └── SalesReportController.java
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   │   │   ├── EcoPointsCalculationService.java  # 🎮 Gamificación
│   │   │   │   │   └── SalesReportService.java
│   │   │   │   └── services/
│   │   │   │
│   │   │   ├── payment/                          # 💳 Pagos
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   │
│   │   │   ├── product/                          # 🏷️ Productos
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── services/
│   │   │   │
│   │   │   ├── shoppingCart/                     # 🛍️ Carrito
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── services/
│   │   │   │
│   │   │   └── user/                             # 👤 Usuarios
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── application.properties            # Config desarrollo
│   │       └── application-prod.properties       # Config producción
│   │
│   └── test/
│       └── java/com/EcoHouse/
│           └── EcoHouseApplicationTests.java
│
├── target/                                       # Archivos compilados
├── pom.xml                                       # Dependencias Maven
├── Dockerfile                                    # Containerización
├── Procfile                                      # AWS Elastic Beanstalk
│
├── readme.md                                     # README principal
├── DOCUMENTATION.md                              # 📚 Este archivo
│
└── Scripts SQL/
    ├── fix_all_corrupted_characters_aws.sql     # Fix caracteres
    ├── clean_database_aws.sql                   # Limpiar BD
    └── load_10_orders_manual_aws.sql            # Cargar datos
```

---

## 🗄️ Modelo de Datos

### Diagrama Entidad-Relación Simplificado

```
┌─────────────┐        ┌──────────────┐        ┌─────────────┐
│  Customer   │───────▶│    Order     │◀───────│   Payment   │
│             │ 1    * │              │ 1    1 │             │
└─────────────┘        └──────────────┘        └─────────────┘
                              │
                              │ 1
                              │
                              │ *
                       ┌──────────────┐
                       │  OrderItem   │
                       └──────────────┘
                              │
                              │ *
                              │
                              │ 1
                       ┌──────────────┐        ┌─────────────────────┐
                       │   Product    │───────▶│ EnvironmentalData   │
                       │              │ 1    1 │                     │
                       └──────────────┘        └─────────────────────┘
                              │
                ┌─────────────┼─────────────┐
                │             │             │
                ▼             ▼             ▼
         ┌──────────┐  ┌──────────┐  ┌──────────────┐
         │ Category │  │  Brand   │  │Certification │
         └──────────┘  └──────────┘  └──────────────┘
```

### Entidades Principales

#### 👤 **Customer** (Usuario/Cliente)
```java
- id: Long (PK)
- email: String (UNIQUE)
- firstName: String
- lastName: String
- password: String (encrypted)
- userType: Enum (CUSTOMER, BRAND_ADMIN)
- phone: String
- shippingAddress: String
- billingAddress: String
- carbonFootprint: Double
- isActive: Boolean
- createdAt: LocalDateTime
```

#### 🏷️ **Product** (Producto)
```java
- id: Long (PK)
- name: String
- description: Text
- price: BigDecimal
- imageUrl: String
- stock: Integer
- isActive: Boolean
- brand: Brand (ManyToOne)
- category: Category (ManyToOne)
- environmentalData: EnvironmentalData (OneToOne)
- certifications: Set<Certification> (ManyToMany)
```

#### 🌱 **EnvironmentalData** (Datos Ambientales)
```java
- id: Long (PK)
- carbonFootprint: BigDecimal (kg CO2)
- material: String
- countryOfOrigin: String
- energyConsumption: BigDecimal (kWh)
- recyclablePercentage: BigDecimal (%)
- notes: Text
- product: Product (OneToOne)
- createdAt: LocalDateTime
```

#### 🛒 **Order** (Orden)
```java
- id: Long (PK)
- orderNumber: String (UNIQUE)
- customer: Customer (ManyToOne)
- items: List<OrderItem> (OneToMany)
- status: OrderStatus (PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
- totalAmount: BigDecimal
- totalCarbonFootprint: BigDecimal
- ecoPointsEarned: Integer
- shippingAddress: ShippingAddress (Embedded)
- payment: Payment (OneToOne)
- orderDate: Date
- shippingDate: Date
- deliveryDate: Date
```

#### 📦 **OrderItem** (Item de Orden)
```java
- id: Long (PK)
- order: Order (ManyToOne)
- product: Product (ManyToOne)
- quantity: Integer
- unitPrice: BigDecimal
- subtotal: BigDecimal
- itemCarbonFootprint: BigDecimal
- co2Saved: BigDecimal
```

#### 🌍 **ImpactReport** (Reporte de Impacto)
```java
- id: Long (PK)
- customer: Customer (ManyToOne)
- startDate: LocalDateTime
- endDate: LocalDateTime
- totalCO2Saved: BigDecimal
- totalCO2Footprint: BigDecimal
- totalOrders: Integer
- ecoPointsEarned: Integer
- totalAmountSpent: BigDecimal
- sustainableChoicesCount: Integer
- reportType: ReportType (MONTHLY, QUARTERLY, ANNUAL, CUSTOM, ON_DEMAND)
- ecoEfficiencyScore: Integer
- impactLevel: String (BAJO, MODERADO, BUENO, EXCELENTE)
- createdAt: LocalDateTime
```

### Relaciones Clave

| Relación | Tipo | Descripción |
|----------|------|-------------|
| Customer ↔ Order | OneToMany | Un cliente tiene muchas órdenes |
| Order ↔ OrderItem | OneToMany | Una orden tiene muchos items |
| Product ↔ OrderItem | ManyToOne | Un producto puede estar en muchos items |
| Product ↔ EnvironmentalData | OneToOne | Un producto tiene un registro de datos ambientales |
| Product ↔ Category | ManyToOne | Muchos productos de una categoría |
| Product ↔ Brand | ManyToOne | Muchos productos de una marca |
| Product ↔ Certification | ManyToMany | Un producto puede tener varias certificaciones |
| Order ↔ Payment | OneToOne | Una orden tiene un pago |

---

## 🔌 API Endpoints

### Base URL
- **Local:** `http://localhost:9000/api`
- **Producción:** `http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/api`

### Swagger UI
- **Local:** `http://localhost:9000/swagger-ui/index.html`
- **Producción:** `http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/swagger-ui/index.html`

---

### 🏷️ Productos

#### Listar todos los productos
```http
GET /api/products
```
**Parámetros de consulta:**
- `page` (int): Número de página (default: 0)
- `size` (int): Tamaño de página (default: 10)
- `sort` (string): Campo de ordenamiento (default: id)

**Respuesta exitosa (200):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Camiseta Orgánica Básica",
      "description": "Camiseta 100% algodón orgánico...",
      "price": 29.99,
      "stock": 150,
      "brandName": "Patagonia",
      "categoryName": "Camisetas y Polos",
      "environmentalData": {
        "carbonFootprint": 2.5,
        "material": "Algodón orgánico",
        "recyclablePercentage": 95.0
      }
    }
  ],
  "totalElements": 10,
  "totalPages": 1
}
```

#### Obtener producto por ID
```http
GET /api/products/{id}
```

#### Buscar productos por nombre
```http
GET /api/products/search?name={nombre}
```

#### Filtrar por categoría
```http
GET /api/products/category/{categoryId}
```

#### Filtrar por marca
```http
GET /api/products/brand/{brandId}
```

---

### 🛒 Órdenes

#### Crear nueva orden
```http
POST /api/orders
Content-Type: application/json

{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ],
  "shippingAddress": {
    "street": "Main Street",
    "number": "123",
    "city": "Los Angeles",
    "state": "CA",
    "country": "USA",
    "zipCode": "90001"
  }
}
```

#### Listar órdenes con paginación
```http
GET /api/orders?page=0&size=10
```

**Respuesta exitosa (200):**
```json
{
  "content": [
    {
      "id": 1,
      "orderNumber": "ORD-2024-001",
      "customerId": 1,
      "status": "DELIVERED",
      "totalAmount": 259.96,
      "totalCarbonFootprint": 18.7,
      "ecoPointsEarned": 52,
      "orderDate": "2024-10-15T10:30:00",
      "items": [...]
    }
  ],
  "totalPages": 5,
  "totalElements": 50
}
```

#### Obtener orden por ID
```http
GET /api/orders/{id}
```

#### Actualizar estado de orden
```http
PATCH /api/orders/{id}/status
Content-Type: application/json

{
  "status": "SHIPPED"
}
```

---

### 🛍️ Carrito de Compras

#### Agregar item al carrito
```http
POST /api/cart/customer/{customerId}/items?productId={productId}&quantity={quantity}
```

**Respuesta exitosa (201):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "product": {...},
      "quantity": 2,
      "subtotal": 59.98
    }
  ],
  "totalPrice": 59.98,
  "estimatedCarbonFootprint": 5.0
}
```

#### Ver carrito del cliente
```http
GET /api/cart/customer/{customerId}
```

#### Actualizar cantidad de item
```http
PUT /api/cart/customer/{customerId}/items/{itemId}?quantity={newQuantity}
```

#### Eliminar item del carrito
```http
DELETE /api/cart/customer/{customerId}/items/{itemId}
```

#### Limpiar carrito
```http
DELETE /api/cart/customer/{customerId}/clear
```

---

### 🌍 Reportes de Impacto Ambiental

#### Generar reporte para un cliente
```http
POST /api/reports/generate
Content-Type: application/json

{
  "customerId": 1,
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59",
  "reportType": "ANNUAL"
}
```

**Tipos de reporte:**
- `MONTHLY`: Reporte mensual
- `QUARTERLY`: Reporte trimestral
- `ANNUAL`: Reporte anual
- `CUSTOM`: Período personalizado
- `ON_DEMAND`: A demanda

**Respuesta exitosa (201):**
```json
{
  "id": 1,
  "customerId": 1,
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59",
  "totalCO2Saved": 45.6,
  "totalCO2Footprint": 152.0,
  "totalOrders": 12,
  "ecoPointsEarned": 450,
  "totalAmountSpent": 1250.50,
  "sustainableChoicesCount": 36,
  "reportType": "ANNUAL",
  "ecoEfficiencyScore": 85,
  "impactLevel": "EXCELENTE",
  "averageOrderCO2": 12.67,
  "averageOrderValue": 104.21,
  "sustainabilityPercentage": 300,
  "categoryImpactBreakdown": {
    "Camisetas y Polos": 15.2,
    "Pantalones y Jeans": 28.4
  },
  "topSustainableProducts": [
    {
      "productId": 1,
      "productName": "Camiseta Orgánica Básica",
      "co2Saved": 12.5,
      "timesPurchased": 5,
      "totalSpent": 149.95
    }
  ],
  "monthlyTrend": [...],
  "achievements": {
    "badges": ["🌳 Eco Héroe"],
    "sustainabilityRank": 1,
    "nextMilestone": "Eco Champion (1000 puntos)",
    "progressToNextLevel": 45
  }
}
```

#### Obtener todos los reportes de un cliente
```http
GET /api/reports/customer/{customerId}
```

#### Obtener reporte por ID
```http
GET /api/reports/{reportId}
```

---

### 📊 Reportes de Ventas

#### Estadísticas de ventas totales
```http
GET /api/sales/total
```

**Respuesta exitosa (200):**
```json
{
  "totalSales": 25680.50,
  "totalOrders": 156,
  "averageOrderValue": 164.62,
  "currency": "USD"
}
```

#### Ventas por cliente
```http
GET /api/sales/by-customer
```

**Respuesta exitosa (200):**
```json
[
  {
    "customerId": 1,
    "customerName": "Ana García",
    "totalSales": 1250.50,
    "totalOrders": 12,
    "averageOrderValue": 104.21
  }
]
```

#### Ventas de un cliente específico
```http
GET /api/sales/customer/{customerId}
```

---

### 📂 Categorías

#### Listar todas las categorías
```http
GET /api/categories
```

#### Obtener categoría por ID
```http
GET /api/categories/{id}
```

---

### 🏪 Marcas

#### Listar todas las marcas
```http
GET /api/brands
```

#### Obtener marca por ID
```http
GET /api/brands/{id}
```

---

### 🔐 Autenticación

#### Registro de usuario
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePass123!",
  "firstName": "Juan",
  "lastName": "Pérez"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePass123!"
}
```

---

## ⚙️ Configuración

### Variables de Entorno

#### Desarrollo Local (`application.properties`)
```properties
# Server
server.port=9000

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ecohouse_bd
spring.datasource.username=root
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

#### Producción AWS (`application-prod.properties`)
```properties
# Server
server.port=5000

# Database (AWS RDS)
spring.datasource.url=${RDS_URL}
spring.datasource.username=${RDS_USERNAME}
spring.datasource.password=${RDS_PASSWORD}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Activar perfil
spring.profiles.active=prod
```

### Configuración de Base de Datos

#### Crear Base de Datos Local
```sql
CREATE DATABASE ecohouse_bd CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'ecohouse_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON ecohouse_bd.* TO 'ecohouse_user'@'localhost';
FLUSH PRIVILEGES;
```

---

## 🚀 Instalación y Ejecución

### Pre-requisitos

- **Java 21 (LTS)** - [Descargar OpenJDK](https://adoptium.net/)
- **Maven 3.8+** - [Descargar Maven](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Descargar MySQL](https://dev.mysql.com/downloads/)
- **Git** - [Descargar Git](https://git-scm.com/downloads)

### Verificar instalaciones
```bash
java -version    # Debe mostrar Java 21
mvn -version     # Debe mostrar Maven 3.8+
mysql --version  # Debe mostrar MySQL 8.0+
```

---

### Paso 1: Clonar el Repositorio

```bash
git clone https://github.com/belisabel/ecohouse.git
cd ecohouse/back
```

---

### Paso 2: Configurar Base de Datos

```bash
# Conectar a MySQL
mysql -u root -p

# Crear base de datos
CREATE DATABASE ecohouse_bd CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

---

### Paso 3: Configurar application.properties

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecohouse_bd
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

---

### Paso 4: Compilar el Proyecto

```bash
mvn clean install
```

---

### Paso 5: Ejecutar la Aplicación

#### Opción A: Con Maven
```bash
mvn spring-boot:run
```

#### Opción B: Ejecutar el JAR
```bash
java -jar target/EcoHouse-0.0.1-SNAPSHOT.jar
```

---

### Paso 6: Verificar que está Funcionando

#### Verificar en el navegador:
```
http://localhost:9000/swagger-ui/index.html
```

#### Verificar con cURL:
```bash
curl http://localhost:9000/api/products
```

**Respuesta esperada:** JSON con lista de productos

---

### Paso 7: Datos de Ejemplo (Opcional)

La aplicación carga automáticamente datos de ejemplo al iniciar


---

## 🌐 Despliegue

### Despliegue en AWS Elastic Beanstalk

#### Pre-requisitos
- Cuenta de AWS activa
- AWS CLI configurado
- EB CLI instalado

#### Paso 1: Instalar EB CLI
```bash
pip install awsebcli
```

#### Paso 2: Inicializar Elastic Beanstalk
```bash
eb init -p "Corretto 21" ecohouse
```

#### Paso 3: Crear Entorno
```bash
eb create ecohouse-env
```

#### Paso 4: Configurar Variables de Entorno
```bash
eb setenv RDS_URL="jdbc:mysql://your-rds-endpoint:3306/ecohouse_bd" \
          RDS_USERNAME="admin" \
          RDS_PASSWORD="your_secure_password" \
          SPRING_PROFILES_ACTIVE="prod"
```

#### Paso 5: Desplegar
```bash
eb deploy
```

#### Paso 6: Abrir Aplicación
```bash
eb open
```

---

### CI/CD con GitHub Actions

El proyecto incluye un workflow de CI/CD que se ejecuta automáticamente al hacer push a `main`:

**Archivo:** `.github/workflows/deploy.yml`

**Pasos automáticos:**
1. ✅ Checkout del código
2. ✅ Setup de Java 21
3. ✅ Build con Maven
4. ✅ Ejecución de tests
5. ✅ Deploy a AWS Elastic Beanstalk
6. ✅ Health check

---

## 🧪 Testing

### Ejecutar Tests

#### Todos los tests
```bash
mvn test
```

#### Tests con cobertura
```bash
mvn test jacoco:report
```

Ver reporte de cobertura en: `target/site/jacoco/index.html`

#### Tests de integración
```bash
mvn verify
```

---

### Tests Manuales con cURL

#### Crear una orden
```bash
curl -X POST http://localhost:9000/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "items": [
      {"productId": 1, "quantity": 2}
    ],
    "shippingAddress": {
      "street": "Main St",
      "number": "123",
      "city": "LA",
      "state": "CA",
      "country": "USA",
      "zipCode": "90001"
    }
  }'
```

#### Generar reporte de impacto
```bash
curl -X POST http://localhost:9000/api/reports/generate \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59",
    "reportType": "ANNUAL"
  }'
```

---

## 🔒 Seguridad

### Autenticación y Autorización

#### Spring Security
- ✅ Autenticación basada en JWT (pendiente implementación completa)
- ✅ Roles: `CUSTOMER`, `BRAND_ADMIN`
- ✅ Password encryption con BCrypt
- ✅ CORS configurado

#### Configuración de Seguridad

**Endpoints Públicos:**
- `/api/products/**` (lectura)
- `/api/categories/**` (lectura)
- `/api/brands/**` (lectura)
- `/swagger-ui/**`
- `/api-docs/**`

**Endpoints Protegidos:**
- `/api/orders/**` (requiere autenticación)
- `/api/cart/**` (requiere autenticación)
- `/api/reports/**` (requiere autenticación)
- `/api/admin/**` (requiere rol ADMIN)

---

### Mejores Prácticas de Seguridad

✅ **Contraseñas:**
- Encriptación con BCrypt
- Validación de complejidad mínima

✅ **Base de Datos:**
- Parámetros preparados (previene SQL Injection)
- Validación de entrada

✅ **API:**
- Validación de datos con Bean Validation
- Rate limiting (pendiente)
- HTTPS en producción

---

## 🎮 Sistema de EcoPoints

### Fórmula de Cálculo

```
EcoPoints = (Base + CO2 + Productos + Gasto) × Multiplicador
```

### Componentes

#### 1. **Puntos Base** = 10
Por cada orden completada.

#### 2. **CO2 Points** = CO2 ahorrado (kg)
```
CO2 Saved = Total Footprint × 0.30
Points = CO2 Saved (redondeado)
```

#### 3. **Product Points** = Productos sostenibles × 5
```
Count = productos con environmental_data
Points = Count × 5
```

#### 4. **Spending Points** = Monto total / 10
```
Points = totalAmount / 10 (redondeado)
```

#### 5. **Multiplicador** según Impact Level

| Impact Level | Ratio CO2 | Multiplicador |
|--------------|-----------|---------------|
| EXCELENTE | ≥ 80% | ×1.5 |
| BUENO | ≥ 60% | ×1.2 |
| MODERADO | ≥ 40% | ×1.0 |
| BAJO | < 40% | ×0.8 |
| SIN_DATOS | N/A | ×0.5 |

---

### Ejemplo de Cálculo

**Orden:**
- Total: $169.97
- Productos: 3 sostenibles
- CO2 Footprint: 9.5 kg
- CO2 Saved: 2.85 kg
- Ratio: 30% → BAJO

**Cálculo:**
```
Base:      10 puntos
CO2:       2 puntos (2.85 kg)
Productos: 15 puntos (3 × 5)
Gasto:     16 puntos ($169.97 / 10)
────────────────────
Subtotal:  43 puntos
× 0.8 (BAJO) = 34 puntos
```

---

### Niveles EcoHero

| Nivel | Puntos | Badge | Beneficios |
|-------|--------|-------|------------|
| 🌱 Eco Novato | 0-99 | Principiante | Bienvenida |
| 🌿 Eco Consciente | 100-499 | Comprometido | 5% descuento |
| 🌳 Eco Héroe | 500-999 | Defensor | 10% descuento |
| 🏆 Eco Champion | 1000-4999 | Campeón | 15% descuento + envío gratis |
| 👑 Eco Legend | 5000+ | Leyenda | 20% descuento + VIP |

---

## 🤝 Contribución

### Proceso de Contribución

1. **Fork** el repositorio
2. **Crea** una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. **Push** a la rama (`git push origin feature/AmazingFeature`)
5. **Abre** un Pull Request

### Convención de Commits

Seguimos [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: Agregar endpoint de reportes mensuales
fix: Corregir cálculo de CO2 en OrderService
docs: Actualizar README con instrucciones de deploy
refactor: Mejorar estructura de DTOs
test: Agregar tests para EcoPointsService
```

### Estándares de Código

- ✅ Java Code Conventions
- ✅ Clean Code principles
- ✅ SOLID principles
- ✅ Documentación Javadoc
- ✅ Tests unitarios (>80% cobertura)

---

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 👥 Equipo de Desarrollo

### Backend Team
- **Developer 1** - Backend Lead
- **Developer 2** - API Development
- **Developer 3** - Database Design

### Contacto
- **Email:** contact@ecohouse.com
- **GitHub:** [@belisabel](https://github.com/belisabel)

---

## 📞 Soporte

### Problemas Comunes

#### Error: "Port 9000 already in use"
```bash
# Windows
netstat -ano | findstr :9000
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:9000 | xargs kill -9
```

#### Error: "Failed to connect to database"
- Verificar que MySQL esté corriendo
- Verificar credenciales en `application.properties`
- Verificar que la base de datos existe

#### Error de compilación
```bash
# Limpiar y recompilar
mvn clean install -U
```

---

## 🔄 Roadmap

### Versión 2.0 (Próxima)
- [ ] Implementación completa de JWT
- [ ] Sistema de notificaciones por email
- [ ] Integración con pasarelas de pago reales
- [ ] Dashboard de analytics avanzado
- [ ] API de productos recomendados (ML)
- [ ] Sistema de reviews y ratings
- [ ] Wishlist de productos
- [ ] Programa de referidos

### Versión 3.0 (Futuro)
- [ ] Aplicación móvil nativa
- [ ] Chatbot con IA para recomendaciones
- [ ] Blockchain para tracking de supply chain
- [ ] Integración con IoT devices
- [ ] Marketplace multi-vendor

---

## 🙏 Agradecimientos

- Spring Boot Team por el excelente framework
- Comunidad Open Source
- NoCountry por la oportunidad
- Todos los contribuidores

---

## 📚 Referencias

### Documentación Oficial
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

### Recursos Adicionales
- [Spring Boot Best Practices](https://www.baeldung.com/spring-boot-best-practices)
- [REST API Design](https://restfulapi.net/)
- [Clean Code](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)

---

<div align="center">

**¿Construyendo un futuro más sostenible, una compra a la vez! 🌍💚**

[⬆ Volver al inicio](#-ecohouse-backend---documentación-completa)

</div>

