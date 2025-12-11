# 🚀 Guía Rápida de Inicio - EcoHouse Backend

> Documentación rápida para desarrolladores que quieren empezar a trabajar con el proyecto inmediatamente.

---

## ⚡ Inicio Rápido (5 minutos)

### 1. Pre-requisitos
```bash
java -version   # Debe ser Java 21
mvn -version    # Debe ser Maven 3.8+
mysql --version # Debe ser MySQL 8.0+
```

### 2. Clonar y Configurar
```bash
# Clonar
git clone https://github.com/belisabel/ecohouse.git
cd ecohouse/back

# Crear BD
mysql -u root -p
CREATE DATABASE ecohouse_bd CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;

# Editar credenciales
nano src/main/resources/application.properties
# Cambiar: spring.datasource.password=TU_PASSWORD
```

### 3. Ejecutar
```bash
mvn spring-boot:run
```

### 4. Verificar
```
http://localhost:9000/swagger-ui/index.html
```

✅ **¡Listo! Tu backend está funcionando.**

---

## 🧪 Testing Rápido

### Probar Productos
```bash
curl http://localhost:9000/api/products | jq
```

### Probar Crear Orden
```bash
curl -X POST http://localhost:9000/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "items": [{"productId": 1, "quantity": 2}],
    "shippingAddress": {
      "street": "Main St",
      "number": "123",
      "city": "LA",
      "state": "CA",
      "country": "USA",
      "zipCode": "90001"
    }
  }' | jq
```

### Probar Reporte de Impacto
```bash
curl -X POST http://localhost:9000/api/reports/generate \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 2,
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59",
    "reportType": "ANNUAL"
  }' | jq
```

---

## 📊 Datos de Ejemplo Precargados

Al iniciar la aplicación, se cargan automáticamente:

### Usuarios
| Email | Password | Rol |
|-------|----------|-----|
| admin@ecohouse.com | Admin2024! | BRAND_ADMIN |
| ana.garcia@gmail.com | password123 | CUSTOMER |
| carlos.lopez@gmail.com | password123 | CUSTOMER |
| maria.rodriguez@gmail.com | password123 | CUSTOMER |

### Datos
- ✅ 10 Marcas (Patagonia, EcoAlf, etc.)
- ✅ 10 Categorías (Camisetas, Jeans, etc.)
- ✅ 10 Certificaciones (GOTS, Fair Trade, etc.)
- ✅ 11 Clientes (1 admin + 10 usuarios)
- ✅ 10 Productos con datos ambientales
- ✅ 20 Órdenes completadas

---

## 🔧 Comandos Útiles

### Maven
```bash
mvn clean                    # Limpiar
mvn compile                  # Compilar
mvn test                     # Tests
mvn package                  # Empaquetar JAR
mvn spring-boot:run          # Ejecutar
mvn clean install -DskipTests # Build sin tests
```

### Base de Datos
```bash
# Conectar
mysql -u root -p ecohouse_bd

# Ver tablas
SHOW TABLES;

# Ver productos
SELECT id, name, price FROM products LIMIT 5;

# Ver órdenes
SELECT id, order_number, status, total_amount FROM orders LIMIT 5;

# Limpiar datos (¡CUIDADO!)
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;
```

---

## 🐛 Troubleshooting Rápido

### Puerto 9000 ocupado
```bash
# Windows
netstat -ano | findstr :9000
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:9000 | xargs kill -9
```

### Error de conexión a BD
```bash
# Verificar MySQL corriendo
systemctl status mysql    # Linux
mysql.server status       # Mac
sc query MySQL            # Windows

# Verificar credenciales
mysql -u root -p
```

### Recompilar desde cero
```bash
mvn clean install -U -DskipTests
```

---

## 📁 Estructura Básica

```
back/
├── src/main/java/com/EcoHouse/
│   ├── product/        # Productos
│   ├── order/          # Órdenes  
│   ├── impactReport/   # Reportes
│   ├── shoppingCart/   # Carrito
│   ├── user/           # Usuarios
│   └── config/         # Configuración
│
└── src/main/resources/
    └── application.properties  # Config
```

---

## 🌐 URLs Importantes

### Local
- **API Base:** `http://localhost:9000/api`
- **Swagger:** `http://localhost:9000/swagger-ui/index.html`
- **Health Check:** `http://localhost:9000/actuator/health`

### Producción
- **API Base:** `http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/api`
- **Swagger:** `http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/swagger-ui/index.html`

---

## 🎯 Endpoints Más Usados

```http
GET    /api/products              # Listar productos
GET    /api/products/{id}         # Ver producto
POST   /api/orders                # Crear orden
GET    /api/orders?page=0         # Listar órdenes
POST   /api/reports/generate      # Generar reporte
GET    /api/reports/customer/{id} # Reportes del cliente
POST   /api/cart/customer/{id}/items # Agregar al carrito
GET    /api/cart/customer/{id}    # Ver carrito
GET    /api/sales/total           # Estadísticas ventas
```

---

## 💡 Tips

### Development Mode
- ✅ `spring.jpa.show-sql=true` → Ver queries SQL
- ✅ `spring.jpa.properties.hibernate.format_sql=true` → SQL formateado
- ✅ Hot reload con Spring DevTools

### Testing con Swagger
1. Abrir `http://localhost:9000/swagger-ui/index.html`
2. Expandir endpoint
3. Clic en "Try it out"
4. Llenar parámetros
5. Clic en "Execute"

### Ver Logs
```bash
# Logs en consola con colores
mvn spring-boot:run

# Filtrar logs específicos
mvn spring-boot:run | grep "EcoHouse"
```

---

## 📚 Documentación Completa

Para documentación detallada, ver:
- **[DOCUMENTATION.md](DOCUMENTATION.md)** - Documentación técnica completa
- **[README.md](readme.md)** - Información general del proyecto

---

## 🆘 ¿Necesitas Ayuda?

1. **Revisa Swagger:** `http://localhost:9000/swagger-ui/index.html`
2. **Lee DOCUMENTATION.md:** Documentación completa
3. **Abre un Issue:** [GitHub Issues](https://github.com/belisabel/ecohouse/issues)

---

<div align="center">

**¡Feliz Desarrollo! 🚀**

[⬆ Volver al inicio](#-guía-rápida-de-inicio---ecohouse-backend)

</div>

