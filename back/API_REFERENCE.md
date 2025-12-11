# 🔌 Referencia de API - EcoHouse Backend

> Referencia completa de todos los endpoints de la API REST

---

## 📋 Tabla de Contenidos

- [Información General](#-información-general)
- [Productos](#️-productos)
- [Órdenes](#-órdenes)
- [Carrito de Compras](#️-carrito-de-compras)
- [Reportes de Impacto Ambiental](#-reportes-de-impacto-ambiental)
- [Reportes de Ventas](#-reportes-de-ventas)
- [Categorías](#-categorías)
- [Marcas](#-marcas)
- [Autenticación](#-autenticación)
- [Modelos de Datos](#-modelos-de-datos)
- [Códigos de Error](#️-códigos-de-error)
- [Recursos Adicionales](#-recursos-adicionales)

---

## 📌 Información General

### URLs Base
- **Desarrollo:** `http://localhost:9000/api`
- **Producción:** `http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/api`
- **Swagger Local:** [http://localhost:9000/swagger-ui/index.html](http://localhost:9000/swagger-ui/index.html)
- **Swagger Producción:** [Ver Swagger](http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/swagger-ui/index.html)

### Repositorio y CI/CD
- **Repositorio GitHub:** [https://github.com/belisabel/ecohouse](https://github.com/belisabel/ecohouse)
- **Issues y Bugs:** [GitHub Issues](https://github.com/belisabel/ecohouse/issues)
- **Pull Requests:** [GitHub PRs](https://github.com/belisabel/ecohouse/pulls)
- **CI/CD Pipeline:** GitHub Actions
  - ✅ Despliegue automático a AWS Elastic Beanstalk
  - ✅ Ejecución de tests automáticos
  - ✅ Build con Maven
  - ✅ Deploy en cada push a `main`
  - 📄 Workflow: [`.github/workflows/deploy.yml`](https://github.com/belisabel/ecohouse/blob/main/.github/workflows/deploy.yml)

### Formato de Respuesta
Todas las respuestas son en formato **JSON** (UTF-8).

### Códigos de Estado HTTP

| Código | Significado | Uso |
|--------|-------------|-----|
| **200** | OK | Solicitud exitosa |
| **201** | Created | Recurso creado exitosamente |
| **204** | No Content | Eliminación exitosa |
| **400** | Bad Request | Error en la solicitud del cliente |
| **404** | Not Found | Recurso no encontrado |
| **500** | Internal Server Error | Error del servidor |

### Documentación Relacionada
- 📘 [Documentación Completa](./DOCUMENTATION.md)
- ⚡ [Guía de Inicio Rápido](./QUICKSTART.md)
- 📖 [README Principal](./readme.md)

---

## 🏷️ Productos

### `GET /api/products`
Lista todos los productos con paginación y ordenamiento.

**Parámetros de Query:**
| Parámetro | Tipo | Requerido | Default | Descripción |
|-----------|------|-----------|---------|-------------|
| `page` | integer | No | 0 | Número de página (base 0) |
| `size` | integer | No | 10 | Elementos por página |
| `sort` | string | No | id | Campo de ordenamiento |

**Ejemplo de Request:**
```http
GET /api/products?page=0&size=10&sort=price
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/products?page=0&size=10" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Camiseta Orgánica Básica",
      "description": "Camiseta 100% algodón orgánico certificado GOTS",
      "price": 29.99,
      "imageUrl": "https://example.com/camiseta-organica.jpg",
      "stock": 150,
      "isActive": true,
      "brandName": "Patagonia",
      "categoryName": "Camisetas y Polos",
      "certificationNames": [
        "GOTS (Global Organic Textile Standard)",
        "OEKO-TEX Standard 100"
      ],
      "environmentalData": {
        "id": 1,
        "carbonFootprint": 2.5,
        "material": "Algodón orgánico",
        "countryOfOrigin": "India",
        "energyConsumption": 15.5,
        "recyclablePercentage": 95.0,
        "notes": "Teñido con tintes naturales, libre de químicos tóxicos",
        "createdAt": "2024-12-10T12:00:00"
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 10,
  "last": true,
  "first": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 10,
  "empty": false
}
```

📌 **Ver también:** [Modelo Product](#product)

---

### `GET /api/products/{id}`
Obtiene un producto específico por su ID.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `id` | long | ✅ Sí | ID único del producto |

**Ejemplo de Request:**
```http
GET /api/products/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/products/1" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
{
  "id": 1,
  "name": "Camiseta Orgánica Básica",
  "description": "Camiseta 100% algodón orgánico certificado GOTS",
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
```

**Respuesta de Error (404 Not Found):**
```json
{
  "timestamp": "2024-12-10T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado con ID: 999",
  "path": "/api/products/999"
}
```

---

### `GET /api/products/search`
Busca productos por nombre (búsqueda parcial).

**Parámetros de Query:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `name` | string | ✅ Sí | Nombre o parte del nombre del producto |

**Ejemplo de Request:**
```http
GET /api/products/search?name=camiseta
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/products/search?name=camiseta" \
  -H "Accept: application/json"
```

---

### `GET /api/products/category/{categoryId}`
Filtra productos por categoría.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `categoryId` | long | ✅ Sí | ID de la categoría |

**Ejemplo de Request:**
```http
GET /api/products/category/1
```

📌 **Ver también:** [Obtener Categorías](#get-apicategories)

---

### `GET /api/products/brand/{brandId}`
Filtra productos por marca.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `brandId` | long | ✅ Sí | ID de la marca |

**Ejemplo de Request:**
```http
GET /api/products/brand/1
```

📌 **Ver también:** [Obtener Marcas](#get-apibrands)

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 🛒 Órdenes

### `POST /api/orders`
Crea una nueva orden de compra.

**Request Body:**
```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
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

**Campos del Request:**

| Campo | Tipo | Requerido | Descripción |
|-------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |
| `items` | array | ✅ Sí | Lista de items de la orden (mínimo 1) |
| `items[].productId` | long | ✅ Sí | ID del producto |
| `items[].quantity` | integer | ✅ Sí | Cantidad (debe ser > 0) |
| `shippingAddress` | object | ✅ Sí | Dirección de envío completa |
| `shippingAddress.street` | string | ✅ Sí | Nombre de la calle |
| `shippingAddress.number` | string | ✅ Sí | Número de domicilio |
| `shippingAddress.city` | string | ✅ Sí | Ciudad |
| `shippingAddress.state` | string | ✅ Sí | Estado o provincia |
| `shippingAddress.country` | string | ✅ Sí | País |
| `shippingAddress.zipCode` | string | ✅ Sí | Código postal |

**Ejemplo con cURL:**
```bash
curl -X POST "http://localhost:9000/api/orders" \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "items": [
      {"productId": 1, "quantity": 2},
      {"productId": 3, "quantity": 1}
    ],
    "shippingAddress": {
      "street": "Main Street",
      "number": "123",
      "city": "Los Angeles",
      "state": "CA",
      "country": "USA",
      "zipCode": "90001"
    }
  }'
```

**Respuesta Exitosa (201 Created):**
```json
{
  "id": 21,
  "customerId": 1,
  "orderNumber": "ORD-2024-021",
  "items": [
    {
      "id": 41,
      "productId": 1,
      "productName": "Camiseta Orgánica Básica",
      "quantity": 2,
      "unitPrice": 29.99,
      "totalPrice": 59.98,
      "carbonFootprint": 2.5
    },
    {
      "id": 42,
      "productId": 3,
      "productName": "Vestido Estampado Sostenible",
      "quantity": 1,
      "unitPrice": 89.99,
      "totalPrice": 89.99,
      "carbonFootprint": 5.2
    }
  ],
  "totalAmount": 149.97,
  "totalCarbonFootprint": 10.2,
  "co2Saved": 3.06,
  "status": "PENDING",
  "shippingAddress": {
    "street": "Main Street",
    "number": "123",
    "city": "Los Angeles",
    "state": "CA",
    "country": "USA",
    "zipCode": "90001"
  },
  "payment": {
    "id": 21,
    "amount": 149.97,
    "paymentDate": "2024-12-10T12:00:00"
  },
  "orderDate": "2024-12-10T12:00:00",
  "shippingDate": null,
  "deliveryDate": null,
  "ecoPointsEarned": null
}
```

📌 **Ver también:** [Modelo Order](#order) | [Sistema de EcoPoints](./DOCUMENTATION.md#-sistema-de-ecopoints)

---

### `GET /api/orders`
Lista todas las órdenes con paginación.

**Parámetros de Query:**
| Parámetro | Tipo | Requerido | Default | Descripción |
|-----------|------|-----------|---------|-------------|
| `page` | integer | No | 0 | Número de página |
| `size` | integer | No | 100 | Elementos por página |

**Ejemplo de Request:**
```http
GET /api/orders?page=0&size=20
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/orders?page=0&size=20" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "customerId": 1,
      "orderNumber": "ORD-2024-001",
      "items": [...],
      "totalAmount": 259.96,
      "totalCarbonFootprint": 18.7,
      "co2Saved": 5.61,
      "status": "DELIVERED",
      "ecoPointsEarned": 52,
      "orderDate": "2024-10-15T10:30:00",
      "deliveryDate": "2024-10-20T14:00:00"
    }
  ],
  "totalPages": 1,
  "totalElements": 20,
  "size": 100,
  "number": 0
}
```

---

### `GET /api/orders/{id}`
Obtiene una orden específica por su ID.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `id` | long | ✅ Sí | ID único de la orden |

**Ejemplo de Request:**
```http
GET /api/orders/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/orders/1" \
  -H "Accept: application/json"
```

---

### `PATCH /api/orders/{id}/status`
Actualiza el estado de una orden existente.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `id` | long | ✅ Sí | ID de la orden |

**Request Body:**
```json
{
  "status": "SHIPPED"
}
```

**Estados Válidos:**
| Estado | Descripción |
|--------|-------------|
| `PENDING` | Orden pendiente de procesamiento |
| `PROCESSING` | En proceso de preparación |
| `SHIPPED` | Enviada al cliente |
| `DELIVERED` | Entregada exitosamente |
| `CANCELLED` | Cancelada |

**Ejemplo con cURL:**
```bash
curl -X PATCH "http://localhost:9000/api/orders/1/status" \
  -H "Content-Type: application/json" \
  -d '{"status": "SHIPPED"}'
```

**Respuesta Exitosa (200 OK):**
```json
{
  "id": 1,
  "orderNumber": "ORD-2024-001",
  "status": "SHIPPED",
  "shippingDate": "2024-12-10T12:00:00"
}
```

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 🛍️ Carrito de Compras

### `POST /api/cart/customer/{customerId}/items`
Agrega un producto al carrito del cliente.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |

**Parámetros de Query:**
| Parámetro | Tipo | Requerido | Default | Descripción |
|-----------|------|-----------|---------|-------------|
| `productId` | long | ✅ Sí | - | ID del producto a agregar |
| `quantity` | integer | No | 1 | Cantidad a agregar |

**Ejemplo de Request:**
```http
POST /api/cart/customer/1/items?productId=1&quantity=2
```

**Ejemplo con cURL:**
```bash
curl -X POST "http://localhost:9000/api/cart/customer/1/items?productId=1&quantity=2" \
  -H "Content-Type: application/json"
```

**Respuesta Exitosa (201 Created):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "product": {
        "id": 1,
        "name": "Camiseta Orgánica Básica",
        "price": 29.99,
        "imageUrl": "https://example.com/camiseta-organica.jpg",
        "environmentalData": {
          "carbonFootprint": 2.5
        }
      },
      "quantity": 2,
      "unitPrice": 29.99,
      "subtotal": 59.98
    }
  ],
  "totalPrice": 59.98,
  "estimatedCarbonFootprint": 5.0
}
```

---

### `GET /api/cart/customer/{customerId}`
Obtiene el carrito actual de un cliente.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |

**Ejemplo de Request:**
```http
GET /api/cart/customer/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/cart/customer/1" \
  -H "Accept: application/json"
```

---

### `PUT /api/cart/customer/{customerId}/items/{itemId}`
Actualiza la cantidad de un item en el carrito.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |
| `itemId` | long | ✅ Sí | ID del item en el carrito |

**Parámetros de Query:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `quantity` | integer | ✅ Sí | Nueva cantidad (> 0) |

**Ejemplo de Request:**
```http
PUT /api/cart/customer/1/items/1?quantity=5
```

**Ejemplo con cURL:**
```bash
curl -X PUT "http://localhost:9000/api/cart/customer/1/items/1?quantity=5" \
  -H "Content-Type: application/json"
```

---

### `DELETE /api/cart/customer/{customerId}/items/{itemId}`
Elimina un item específico del carrito.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |
| `itemId` | long | ✅ Sí | ID del item a eliminar |

**Ejemplo de Request:**
```http
DELETE /api/cart/customer/1/items/1
```

**Ejemplo con cURL:**
```bash
curl -X DELETE "http://localhost:9000/api/cart/customer/1/items/1"
```

**Respuesta Exitosa:** `204 No Content`

---

### `DELETE /api/cart/customer/{customerId}/clear`
Vacía completamente el carrito de un cliente.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |

**Ejemplo de Request:**
```http
DELETE /api/cart/customer/1/clear
```

**Ejemplo con cURL:**
```bash
curl -X DELETE "http://localhost:9000/api/cart/customer/1/clear"
```

**Respuesta Exitosa:** `204 No Content`

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 🌍 Reportes de Impacto Ambiental

### `POST /api/reports/generate`
Genera un nuevo reporte de impacto ambiental para un cliente.

**Request Body:**
```json
{
  "customerId": 1,
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59",
  "reportType": "ANNUAL"
}
```

**Campos del Request:**
| Campo | Tipo | Requerido | Descripción |
|-------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |
| `startDate` | datetime | ✅ Sí | Fecha de inicio (formato ISO 8601) |
| `endDate` | datetime | ✅ Sí | Fecha de fin (formato ISO 8601) |
| `reportType` | string | ✅ Sí | Tipo de reporte a generar |

**Tipos de Reporte Disponibles:**
| Tipo | Descripción | Período Sugerido |
|------|-------------|------------------|
| `MONTHLY` | Reporte mensual | 1 mes |
| `QUARTERLY` | Reporte trimestral | 3 meses |
| `ANNUAL` | Reporte anual | 1 año |
| `CUSTOM` | Período personalizado | Cualquier rango |
| `ON_DEMAND` | A demanda | Cualquier rango |

**Ejemplo con cURL:**
```bash
curl -X POST "http://localhost:9000/api/reports/generate" \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59",
    "reportType": "ANNUAL"
  }'
```

**Respuesta Exitosa (201 Created):**
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
  "createdAt": "2024-12-10T12:00:00",
  "averageOrderCO2": 12.67,
  "ecoEfficiencyScore": 85,
  "impactLevel": "EXCELENTE",
  "averageOrderValue": 104.21,
  "sustainabilityPercentage": 300,
  "categoryImpactBreakdown": {
    "Camisetas y Polos": 15.2,
    "Pantalones y Jeans": 28.4,
    "Vestidos y Faldas": 12.8
  },
  "topSustainableProducts": [
    {
      "productId": 1,
      "productName": "Camiseta Orgánica Básica",
      "co2Saved": 12.5,
      "timesPurchased": 5,
      "totalSpent": 149.95,
      "ecoImpactLevel": "EXCELENTE"
    }
  ],
  "monthlyTrend": [
    {
      "month": "2024-01",
      "co2Saved": 3.8,
      "co2Footprint": 12.6,
      "ordersCount": 1,
      "ecoPoints": 38,
      "amountSpent": 104.21
    }
  ],
  "achievements": {
    "badges": [
      "🌳 Eco Héroe - Score de eficiencia >60%",
      "💚 Comprador Comprometido - 10+ órdenes"
    ],
    "sustainabilityRank": 1,
    "nextMilestone": "Eco Champion (1000 puntos)",
    "progressToNextLevel": 45
  }
}
```

📌 **Ver también:** [Modelo ImpactReport](#impactreport) | [Niveles de Impacto](./DOCUMENTATION.md#niveles-ecohero)

---

### `GET /api/reports/customer/{customerId}`
Obtiene todos los reportes generados de un cliente.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |

**Ejemplo de Request:**
```http
GET /api/reports/customer/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/reports/customer/1" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "customerId": 1,
    "reportType": "ANNUAL",
    "totalCO2Saved": 45.6,
    "ecoPointsEarned": 450,
    "impactLevel": "EXCELENTE",
    "createdAt": "2024-12-10T12:00:00"
  },
  {
    "id": 2,
    "customerId": 1,
    "reportType": "MONTHLY",
    "totalCO2Saved": 3.8,
    "ecoPointsEarned": 38,
    "impactLevel": "BUENO",
    "createdAt": "2024-11-01T10:00:00"
  }
]
```

---

### `GET /api/reports/{reportId}`
Obtiene un reporte específico por su ID.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `reportId` | long | ✅ Sí | ID único del reporte |

**Ejemplo de Request:**
```http
GET /api/reports/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/reports/1" \
  -H "Accept: application/json"
```

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 📊 Reportes de Ventas

### `GET /api/sales/total`
Obtiene estadísticas totales de ventas del sistema.

**Ejemplo de Request:**
```http
GET /api/sales/total
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/sales/total" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
{
  "totalSales": 25680.50,
  "totalOrders": 156,
  "averageOrderValue": 164.62,
  "currency": "USD"
}
```

---

### `GET /api/sales/by-customer`
Obtiene ventas agrupadas por cliente.

**Ejemplo de Request:**
```http
GET /api/sales/by-customer
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/sales/by-customer" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "customerId": 1,
    "customerName": "Ana García",
    "totalSales": 1250.50,
    "totalOrders": 12,
    "averageOrderValue": 104.21
  },
  {
    "customerId": 2,
    "customerName": "Carlos López",
    "totalSales": 989.75,
    "totalOrders": 8,
    "averageOrderValue": 123.72
  }
]
```

---

### `GET /api/sales/customer/{customerId}`
Obtiene las ventas de un cliente específico.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `customerId` | long | ✅ Sí | ID del cliente |

**Ejemplo de Request:**
```http
GET /api/sales/customer/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/sales/customer/1" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
{
  "customerId": 1,
  "customerName": "Ana García",
  "totalSales": 1250.50,
  "totalOrders": 12,
  "averageOrderValue": 104.21
}
```

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 📂 Categorías

### `GET /api/categories`
Lista todas las categorías de productos disponibles.

**Ejemplo de Request:**
```http
GET /api/categories
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/categories" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Camisetas y Polos",
    "description": "Camisetas, polos y tops de algodón orgánico y materiales sostenibles",
    "iconUrl": "👕"
  },
  {
    "id": 2,
    "name": "Pantalones y Jeans",
    "description": "Pantalones, jeans y leggins de producción ética y telas ecológicas",
    "iconUrl": "👖"
  },
  {
    "id": 3,
    "name": "Vestidos y Faldas",
    "description": "Vestidos, faldas y prendas femeninas de moda sostenible",
    "iconUrl": "👗"
  }
]
```

---

### `GET /api/categories/{id}`
Obtiene una categoría específica por su ID.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `id` | long | ✅ Sí | ID de la categoría |

**Ejemplo de Request:**
```http
GET /api/categories/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/categories/1" \
  -H "Accept: application/json"
```

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 🏪 Marcas

### `GET /api/brands`
Lista todas las marcas de productos sostenibles.

**Ejemplo de Request:**
```http
GET /api/brands
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/brands" \
  -H "Accept: application/json"
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Patagonia",
    "description": "Ropa outdoor sostenible y activismo ambiental",
    "imageUrl": "https://example.com/patagonia-logo.png",
    "country": "Estados Unidos"
  },
  {
    "id": 2,
    "name": "EcoAlf",
    "description": "Primera marca de moda sostenible del mundo con materiales reciclados",
    "imageUrl": "https://example.com/ecoalf-logo.png",
    "country": "España"
  },
  {
    "id": 3,
    "name": "Tentree",
    "description": "Por cada compra plantan 10 árboles, ropa casual sostenible",
    "imageUrl": "https://example.com/tentree-logo.png",
    "country": "Canadá"
  }
]
```

---

### `GET /api/brands/{id}`
Obtiene una marca específica por su ID.

**Parámetros de Path:**
| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| `id` | long | ✅ Sí | ID de la marca |

**Ejemplo de Request:**
```http
GET /api/brands/1
```

**Ejemplo con cURL:**
```bash
curl -X GET "http://localhost:9000/api/brands/1" \
  -H "Accept: application/json"
```

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 🔐 Autenticación

### `POST /api/auth/register`
Registra un nuevo usuario en el sistema.

**Request Body:**
```json
{
  "email": "nuevo@example.com",
  "password": "SecurePass123!",
  "firstName": "Juan",
  "lastName": "Pérez",
  "phone": "+12025551234"
}
```

**Campos del Request:**
| Campo | Tipo | Requerido | Descripción |
|-------|------|-----------|-------------|
| `email` | string | ✅ Sí | Email único del usuario |
| `password` | string | ✅ Sí | Contraseña (mínimo 8 caracteres) |
| `firstName` | string | ✅ Sí | Nombre del usuario |
| `lastName` | string | ✅ Sí | Apellido del usuario |
| `phone` | string | No | Teléfono de contacto |

**Ejemplo con cURL:**
```bash
curl -X POST "http://localhost:9000/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "nuevo@example.com",
    "password": "SecurePass123!",
    "firstName": "Juan",
    "lastName": "Pérez",
    "phone": "+12025551234"
  }'
```

**Respuesta Exitosa (201 Created):**
```json
{
  "id": 12,
  "email": "nuevo@example.com",
  "firstName": "Juan",
  "lastName": "Pérez",
  "userType": "CUSTOMER",
  "isActive": true,
  "createdAt": "2024-12-10T12:00:00"
}
```

---

### `POST /api/auth/login`
Inicia sesión de usuario en el sistema.

**Request Body:**
```json
{
  "email": "usuario@example.com",
  "password": "SecurePass123!"
}
```

**Campos del Request:**
| Campo | Tipo | Requerido | Descripción |
|-------|------|-----------|-------------|
| `email` | string | ✅ Sí | Email del usuario |
| `password` | string | ✅ Sí | Contraseña del usuario |

**Ejemplo con cURL:**
```bash
curl -X POST "http://localhost:9000/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "password": "SecurePass123!"
  }'
```

**Respuesta Exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "usuario@example.com",
  "firstName": "Juan",
  "lastName": "Pérez",
  "userType": "CUSTOMER"
}
```

**Credenciales de Prueba:**
| Email | Password | Rol |
|-------|----------|-----|
| admin@ecohouse.com | Admin2024! | BRAND_ADMIN |
| ana.garcia@gmail.com | password123 | CUSTOMER |

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 📋 Modelos de Datos

### Product
```typescript
{
  id: number                    // ID único del producto
  name: string                  // Nombre del producto
  description: string           // Descripción detallada
  price: number                 // Precio en USD
  imageUrl: string              // URL de la imagen
  stock: number                 // Cantidad en inventario
  isActive: boolean             // Si está activo para venta
  brandName: string             // Nombre de la marca
  categoryName: string          // Nombre de la categoría
  certificationNames: string[]  // Certificaciones ecológicas
  environmentalData: EnvironmentalData  // Datos ambientales
}
```

📌 **Ver también:** [Obtener Productos](#get-apiproducts)

---

### EnvironmentalData
```typescript
{
  id: number                    // ID único
  carbonFootprint: number       // Huella de carbono en kg CO2
  material: string              // Material principal
  countryOfOrigin: string       // País de origen
  energyConsumption: number     // Consumo energético en kWh
  recyclablePercentage: number  // Porcentaje reciclable (0-100)
  notes: string                 // Notas adicionales
  createdAt: datetime           // Fecha de creación
}
```

---

### Order
```typescript
{
  id: number                    // ID único de la orden
  customerId: number            // ID del cliente
  orderNumber: string           // Número de orden único
  items: OrderItem[]            // Items de la orden
  totalAmount: number           // Monto total en USD
  totalCarbonFootprint: number  // Huella de carbono total
  co2Saved: number              // CO2 ahorrado vs productos convencionales
  status: OrderStatus           // Estado actual de la orden
  shippingAddress: ShippingAddress  // Dirección de envío
  payment: Payment              // Información de pago
  orderDate: datetime           // Fecha de creación
  shippingDate: datetime        // Fecha de envío
  deliveryDate: datetime        // Fecha de entrega
  ecoPointsEarned: number       // EcoPoints ganados
}
```

📌 **Ver también:** [Crear Orden](#post-apiorders) | [Estados de Orden](#estados-válidos)

---

### OrderItem
```typescript
{
  id: number              // ID único del item
  productId: number       // ID del producto
  productName: string     // Nombre del producto
  quantity: number        // Cantidad ordenada
  unitPrice: number       // Precio unitario
  totalPrice: number      // Precio total (unitPrice × quantity)
  carbonFootprint: number // Huella de carbono del producto
}
```

---

### ImpactReport
```typescript
{
  id: number                           // ID único del reporte
  customerId: number                   // ID del cliente
  startDate: datetime                  // Fecha inicio del período
  endDate: datetime                    // Fecha fin del período
  totalCO2Saved: number                // Total CO2 ahorrado en kg
  totalCO2Footprint: number            // Huella total de carbono
  totalOrders: number                  // Número de órdenes
  ecoPointsEarned: number              // EcoPoints totales ganados
  totalAmountSpent: number             // Monto total gastado
  sustainableChoicesCount: number      // Productos sostenibles comprados
  reportType: ReportType               // Tipo de reporte
  ecoEfficiencyScore: number           // Score de eficiencia (0-100)
  impactLevel: string                  // Nivel: BAJO, MODERADO, BUENO, EXCELENTE
  averageOrderCO2: number              // CO2 promedio por orden
  averageOrderValue: number            // Valor promedio por orden
  sustainabilityPercentage: number     // Porcentaje de sostenibilidad
  categoryImpactBreakdown: object      // Impacto por categoría
  topSustainableProducts: ProductImpact[]  // Top productos sostenibles
  monthlyTrend: MonthlyData[]          // Tendencia mensual
  achievements: Achievements           // Logros y badges
  createdAt: datetime                  // Fecha de creación
}
```

📌 **Ver también:** [Generar Reporte](#post-apireportsgenerate) | [Tipos de Reporte](#tipos-de-reporte-disponibles)

---

### ShippingAddress
```typescript
{
  street: string    // Nombre de la calle
  number: string    // Número de domicilio
  city: string      // Ciudad
  state: string     // Estado o provincia
  country: string   // País
  zipCode: string   // Código postal
}
```

---

### Payment
```typescript
{
  id: number          // ID único del pago
  amount: number      // Monto pagado en USD
  paymentDate: datetime  // Fecha del pago
}
```

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## ⚠️ Códigos de Error

### 400 Bad Request
Error en la solicitud del cliente.

**Ejemplo:**
```json
{
  "timestamp": "2024-12-10T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "La cantidad debe ser mayor a 0",
  "path": "/api/cart/customer/1/items"
}
```

**Causas Comunes:**
- Parámetros inválidos o faltantes
- Formato de datos incorrecto
- Validación de negocio fallida

---

### 404 Not Found
Recurso no encontrado.

**Ejemplo:**
```json
{
  "timestamp": "2024-12-10T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado con ID: 999",
  "path": "/api/products/999"
}
```

**Causas Comunes:**
- ID de recurso inexistente
- URL incorrecta
- Recurso eliminado previamente

---

### 500 Internal Server Error
Error interno del servidor.

**Ejemplo:**
```json
{
  "timestamp": "2024-12-10T12:00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Error al procesar la solicitud",
  "path": "/api/orders"
}
```

**Causas Comunes:**
- Error en base de datos
- Excepción no controlada
- Problema de configuración

**Solución:**
- Contactar al administrador del sistema
- Revisar logs del servidor
- Reportar el error con detalles

[⬆ Volver arriba](#-tabla-de-contenidos)

---

## 📚 Recursos Adicionales

### Documentación Interactiva
- 📊 **Swagger UI Local:** [http://localhost:9000/swagger-ui/index.html](http://localhost:9000/swagger-ui/index.html)
- 📊 **Swagger UI Producción:** [Ver Swagger](http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/swagger-ui/index.html)
- 📄 **OpenAPI JSON:** `/v3/api-docs`
- ❤️ **Health Check:** `/actuator/health`

### Documentación del Proyecto
- 📘 **[Documentación Completa](./DOCUMENTATION.md)** - Guía técnica exhaustiva
  - Arquitectura del sistema
  - Stack tecnológico
  - Modelo de datos detallado
  - Configuración y despliegue
  - Sistema de EcoPoints
  - Contribución y estándares

- ⚡ **[Guía de Inicio Rápido](./QUICKSTART.md)** - Setup en 5 minutos
  - Instalación express
  - Testing inmediato
  - Comandos útiles
  - Troubleshooting rápido

- 📖 **[README Principal](./readme.md)** - Información general
  - Características principales
  - Enlaces de despliegue
  - Instrucciones básicas

### Herramientas Recomendadas
- 🧪 **Postman:** Para testing manual de API
- 🐚 **cURL:** Para scripts y automatización
- 🔍 **Swagger UI:** Para exploración interactiva
- 📊 **Insomnia:** Alternativa a Postman

### Soporte y Contacto
- 🐛 **Reportar Bug:** [GitHub Issues](https://github.com/belisabel/ecohouse/issues)
- 💬 **Preguntas:** Abrir un Issue en GitHub
- 📧 **Email:** contact@ecohouse.com

### Ejemplos de Integración
```bash
# Ver todos los productos
curl http://localhost:9000/api/products | jq

# Crear una orden
curl -X POST http://localhost:9000/api/orders \
  -H "Content-Type: application/json" \
  -d @order.json | jq

# Generar reporte de impacto
curl -X POST http://localhost:9000/api/reports/generate \
  -H "Content-Type: application/json" \
  -d @report-request.json | jq

# Ver estadísticas de ventas
curl http://localhost:9000/api/sales/total | jq
```

### Colecciones de Testing
- 📦 **Colección Postman:** [Descargar](./postman_collection.json) _(pendiente)_
- 🧪 **Scripts de Testing:** Ver carpeta `/test`

[⬆ Volver arriba](#-tabla-de-contenidos)

---

<div align="center">

## 🌍 EcoHouse API

**Construyendo un futuro más sostenible, una compra a la vez**

---

**Versión API:** v1.0  
**Última Actualización:** Diciembre 2024  
**Documentación:** [docs.ecohouse.com](./DOCUMENTATION.md)

---

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![API](https://img.shields.io/badge/API-REST-blue.svg)](./API_REFERENCE.md)
[![Docs](https://img.shields.io/badge/Docs-Complete-success.svg)](./DOCUMENTATION.md)

---

[📘 Documentación](./DOCUMENTATION.md) • 
[⚡ Quick Start](./QUICKSTART.md) • 
[🌐 Swagger](http://localhost:9000/swagger-ui/index.html) • 
[🐛 Reportar Bug](https://github.com/belisabel/ecohouse/issues)

---

**© 2024 EcoHouse. Todos los derechos reservados.**

</div>
