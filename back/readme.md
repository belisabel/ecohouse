# EcoHouse Backend API

RESTful API for EcoHouse environmental impact tracking application.

## Features

- Environmental impact reports generation
- CO2 tracking and carbon footprint calculation
- Eco points system
- Sustainable products tracking
- User management and authentication

## Tech Stack

- Java 21
- Spring Boot 3.5.7
- MySQL 8.0
- AWS Elastic Beanstalk
- GitHub Actions CI/CD

## Deployment

The application is automatically deployed to AWS Elastic Beanstalk on every push to the `main` branch.

- **Production URL**: http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com
- **Swagger UI**: http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/swagger-ui/index.html

## CI/CD Pipeline

GitHub Actions workflow handles:
- Automated testing
- Maven build
- Deployment to AWS
- Health check verification

## 🚨 Production Fix Required

**Before deploying to AWS**, you must run these SQL scripts on RDS:

```bash
# 1. Fix environmental_data constraint
mysql -h your-rds-host -u admin -p ecohouse_bd < fix_environmental_data_constraint.sql

# 2. Fix cart_items list_index (CRITICAL for orders)
mysql -h your-rds-host -u admin -p ecohouse_bd < fix_cart_list_index.sql
```

**Why?** Legacy data has NULL values in `list_index` column causing 500 errors when creating orders.

**Detailed instructions**: See [INSTRUCCIONES_FIX_AWS.md](INSTRUCCIONES_FIX_AWS.md)

---

## Local Development

```bash
# Clone repository
git clone https://github.com/belisabel/ecohouse.git

# Navigate to backend
cd ecohouse/back

# Run cleanup scripts (REQUIRED)
mysql -u root -p ecohouse_bd < fix_environmental_data_constraint.sql
mysql -u root -p ecohouse_bd < fix_cart_list_index.sql

# Run with Maven
mvn spring-boot:run
```

## Testing

```bash
# Run tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## API Documentation

Full API documentation available at `/swagger-ui/index.html` when running.

## 📚 Documentación del Proyecto

### Guías de Inicio Rápido
- **[QUICKSTART.md](./QUICKSTART.md)** - Guía de 10 minutos para testing del sistema de Impact Report
  - Setup rápido en 3 pasos
  - Testing completo en 10 pasos
  - Troubleshooting común

### Documentación Técnica
- **[FLUJO_SISTEMA.md](./FLUJO_SISTEMA.md)** - Explicación detallada del flujo de órdenes e impact reports
  - Diagramas de flujo
  - Explicación paso a paso
  - Cálculos de métricas

- **[TESTING_IMPACT_REPORT.md](./TESTING_IMPACT_REPORT.md)** - Guía completa de testing
  - Tests manuales en Swagger
  - Tests automatizados con cURL
  - Casos de prueba específicos

- **[README_IMPACT_REPORT.md](./README_IMPACT_REPORT.md)** - Overview del sistema de reportes
  - Arquitectura del módulo
  - Endpoints principales
  - Algoritmos de cálculo

### Solución de Problemas
- **[SOLUCION_LIST_INDEX.md](./SOLUCION_LIST_INDEX.md)** - Fix para error de `list_index` en cart_items
  - Análisis técnico del problema
  - Solución implementada
  - Alternativas consideradas

- **[INSTRUCCIONES_FIX_AWS.md](./INSTRUCCIONES_FIX_AWS.md)** - Guía completa para fix en AWS
  - Proceso de deployment
  - Testing post-fix
  - Plan de rollback

### Scripts SQL
- **[fix_environmental_data_constraint.sql](./fix_environmental_data_constraint.sql)** - Limpia datos huérfanos de environmental_data
- **[fix_cart_list_index.sql](./fix_cart_list_index.sql)** - Asigna índices a items del carrito

### Scripts de Deployment
- **[deploy-with-cleanup.sh](./deploy-with-cleanup.sh)** - Script automatizado de deployment a AWS
  - Limpieza de BD
  - Build y deploy
  - Health checks
  - Testing automatizado

## 🚀 Quick Start

Para empezar a desarrollar o probar el sistema:

```bash
# 1. Limpiar base de datos (OBLIGATORIO primera vez)
mysql -u root -p ecohouse_bd < fix_environmental_data_constraint.sql
mysql -u root -p ecohouse_bd < fix_cart_list_index.sql

# 2. Iniciar aplicación
./mvnw spring-boot:run

# 3. Abrir Swagger UI
# http://localhost:9000/swagger-ui/index.html

# 4. Seguir QUICKSTART.md para testing completo
```

