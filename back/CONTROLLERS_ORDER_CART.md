# 📦 Controllers de Order y ShoppingCart - Documentación

## ✅ CONTROLLERS CREADOS

### 1️⃣ OrderController
**Ruta:** `/api/orders`

Controlador completo para gestión de órdenes/pedidos con todas las operaciones CRUD y cambios de estado.

#### Endpoints disponibles:

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/orders/customer/{customerId}` | Crear nueva orden desde el carrito |
| GET | `/api/orders/{id}` | Obtener orden por ID |
| GET | `/api/orders/customer/{customerId}` | Listar órdenes de un cliente |
| PATCH | `/api/orders/{orderId}/status?status={STATUS}` | Actualizar estado de orden |
| POST | `/api/orders/{orderId}/calculate-impact` | Calcular impacto ambiental |
| POST | `/api/orders/{orderId}/cancel` | Cancelar orden |
| POST | `/api/orders/{orderId}/confirm` | Confirmar orden (PROCESSING) |
| POST | `/api/orders/{orderId}/ship` | Marcar como enviada (SHIPPED) |
| POST | `/api/orders/{orderId}/deliver` | Marcar como entregada (DELIVERED) |

#### Estados de Orden:
- `PENDING` - Pendiente
- `PROCESSING` - En proceso
- `SHIPPED` - Enviada
- `DELIVERED` - Entregada
- `CANCELLED` - Cancelada
- `REFUNDED` - Reembolsada

---

### 2️⃣ ShoppingCartController
**Ruta:** `/api/cart`

Controlador completo para gestión del carrito de compras con operaciones para agregar, actualizar y eliminar items.

#### Endpoints disponibles:

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cart/customer/{customerId}` | Obtener carrito completo |
| POST | `/api/cart/customer/{customerId}/items` | Agregar item al carrito |
| DELETE | `/api/cart/customer/{customerId}/items/{productId}` | Eliminar item del carrito |
| PUT | `/api/cart/customer/{customerId}/items/{productId}` | Actualizar cantidad de item |
| PATCH | `/api/cart/customer/{customerId}/items/{productId}/decrease` | Disminuir cantidad en 1 |
| PATCH | `/api/cart/customer/{customerId}/items/{productId}/increase` | Aumentar cantidad en 1 |
| DELETE | `/api/cart/customer/{customerId}` | Limpiar todo el carrito |
| GET | `/api/cart/customer/{customerId}/total` | Obtener total del carrito |
| GET | `/api/cart/customer/{customerId}/count` | Obtener cantidad de items |
| GET | `/api/cart/customer/{customerId}/items/{productId}/exists` | Verificar si item existe |
| GET | `/api/cart/customer/{customerId}/summary` | Resumen (total + cantidad) |

---

## 📝 EJEMPLOS DE USO

### OrderController

#### Crear nueva orden
```http
POST http://localhost:9000/api/orders/customer/1
```

#### Obtener orden por ID
```http
GET http://localhost:9000/api/orders/5
```

#### Listar órdenes de un cliente
```http
GET http://localhost:9000/api/orders/customer/1
```

#### Actualizar estado de orden
```http
PATCH http://localhost:9000/api/orders/5/status?status=SHIPPED
```

#### Calcular impacto ambiental
```http
POST http://localhost:9000/api/orders/5/calculate-impact
```

#### Cancelar orden
```http
POST http://localhost:9000/api/orders/5/cancel
```

---

### ShoppingCartController

#### Obtener carrito
```http
GET http://localhost:9000/api/cart/customer/1
```

#### Agregar producto al carrito
```http
POST http://localhost:9000/api/cart/customer/1/items?productId=10&quantity=2
```

#### Eliminar producto del carrito
```http
DELETE http://localhost:9000/api/cart/customer/1/items/10
```

#### Actualizar cantidad
```http
PUT http://localhost:9000/api/cart/customer/1/items/10?quantity=5
```

#### Aumentar cantidad en 1
```http
PATCH http://localhost:9000/api/cart/customer/1/items/10/increase
```

#### Disminuir cantidad en 1
```http
PATCH http://localhost:9000/api/cart/customer/1/items/10/decrease
```

#### Limpiar carrito
```http
DELETE http://localhost:9000/api/cart/customer/1
```

#### Obtener total del carrito
```http
GET http://localhost:9000/api/cart/customer/1/total
```
**Respuesta:**
```json
{
  "customerId": 1,
  "total": 150.50
}
```

#### Obtener cantidad de items
```http
GET http://localhost:9000/api/cart/customer/1/count
```
**Respuesta:**
```json
{
  "customerId": 1,
  "itemCount": 3
}
```

#### Resumen del carrito
```http
GET http://localhost:9000/api/cart/customer/1/summary
```
**Respuesta:**
```json
{
  "customerId": 1,
  "total": 150.50,
  "itemCount": 3,
  "isEmpty": false
}
```

---

## 🎯 CARACTERÍSTICAS

### OrderController
- ✅ CRUD completo de órdenes
- ✅ Gestión de estados con endpoints dedicados
- ✅ Cálculo de impacto ambiental
- ✅ Usa `OrderResponse` para respuestas
- ✅ Documentado con Swagger/OpenAPI
- ✅ CORS configurado para frontend

### ShoppingCartController
- ✅ Operaciones completas del carrito
- ✅ Incremento/decremento de cantidades
- ✅ Endpoints de utilidad (total, conteo, verificación)
- ✅ Respuestas en formato JSON con mapas
- ✅ Documentado con Swagger/OpenAPI
- ✅ CORS configurado para frontend

---

## 🚀 PRÓXIMOS PASOS

1. **Reiniciar la aplicación** Spring Boot
2. **Acceder a Swagger**: http://localhost:9000/swagger-ui/index.html
3. **Probar los endpoints**:
   - Crear un carrito y agregar productos
   - Crear una orden desde el carrito
   - Actualizar estados de la orden
   - Calcular impacto ambiental

---

## ✅ ESTADO DEL PROYECTO

- ✅ OrderController creado y compilado
- ✅ ShoppingCartController creado y compilado
- ✅ Sin errores de compilación
- ✅ Todos los endpoints documentados
- ✅ CORS configurado
- ✅ Integración con servicios existentes

