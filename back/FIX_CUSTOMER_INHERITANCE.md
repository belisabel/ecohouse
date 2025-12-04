# 🔧 Solución: Problema de Herencia JOINED con Customer

## ❌ PROBLEMA IDENTIFICADO

Cuando se creaba un `Customer`, Hibernate estaba:
- ✅ Insertando en la tabla `users`
- ❌ **NO** insertando en la tabla `customers`
- ❌ Intentaba insertar **dos veces** en `users` causando errores de duplicado

### Error en logs:
```
Duplicate entry 'maria@gmail.com' for key 'users.UK6dotkott2kjsp8vw4d0m25fb7'
```

## 🔍 CAUSA RAÍZ

La estrategia de herencia `JOINED` requiere:
1. Una tabla `users` (clase padre)
2. Una tabla `customers` (clase hija) con FK a `users`
3. La anotación `@PrimaryKeyJoinColumn` en la clase hija

**Faltaba:** La anotación `@PrimaryKeyJoinColumn(name = "id")` en `Customer.java`

## ✅ SOLUCIONES APLICADAS

### 1. Agregado `@PrimaryKeyJoinColumn` en Customer.java

```java
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")  // ← AGREGADO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {
    // ...campos específicos de Customer
}
```

### 2. Corregido el método `createCustomer`

```java
@Override
public Customer createCustomer(User user) {
    Customer customer = new Customer();
    customer.setEmail(user.getEmail());
    customer.setPassword(user.getPassword());
    customer.setFirstName(user.getFirstName());
    customer.setLastName(user.getLastName());
    customer.setUserType(user.getUserType());
    customer.setCreatedAt(user.getCreatedAt());
    customer.setUpdatedAt(user.getUpdatedAt());
    customer.setIsActive(true);  // ← AGREGADO
    
    // JPA automáticamente guardará en users Y customers con el mismo ID
    return customerRepository.save(customer);
}
```

### 3. Eliminadas tablas antiguas y recreadas

- Eliminé las tablas `users` y `customers` con estructura incorrecta
- Cambié `spring.jpa.hibernate.ddl-auto=create` temporalmente
- Hibernate recreará las tablas con la estructura correcta

## 📊 ESTRUCTURA CORRECTA DE LAS TABLAS

### Tabla `users` (clase padre)
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(50),
    is_active BOOLEAN,
    created_at DATETIME,
    updated_at DATETIME
);
```

### Tabla `customers` (clase hija)
```sql
CREATE TABLE customers (
    id BIGINT PRIMARY KEY,  -- ← Mismo ID que users (NO auto_increment)
    shipping_address VARCHAR(255),
    billing_address VARCHAR(255),
    phone VARCHAR(20),
    carbon_footprint DOUBLE,
    FOREIGN KEY (id) REFERENCES users(id)  -- ← Relación con users
);
```

## 🎯 CÓMO FUNCIONA LA HERENCIA JOINED

1. **Al guardar un Customer:**
   ```java
   Customer customer = new Customer();
   customer.setEmail("test@example.com");
   customer.setShippingAddress("Calle 123");
   customerRepository.save(customer);
   ```

2. **Hibernate ejecuta 2 INSERT:**
   ```sql
   -- Primero inserta en users y obtiene el ID generado
   INSERT INTO users (email, first_name, ...) VALUES (...);
   -- ID generado: 1
   
   -- Luego inserta en customers usando el MISMO ID
   INSERT INTO customers (id, shipping_address, ...) VALUES (1, 'Calle 123', ...);
   ```

3. **Al leer un Customer:**
   ```sql
   SELECT u.*, c.*
   FROM users u
   INNER JOIN customers c ON u.id = c.id
   WHERE u.id = 1;
   ```

## 🚀 PRÓXIMOS PASOS

1. **Reiniciar la aplicación** para que Hibernate cree las tablas
2. **Verificar en logs** que se crean ambas tablas:
   ```
   Hibernate: create table users (...)
   Hibernate: create table customers (...)
   ```
3. **Probar crear un Customer** desde Swagger
4. **Verificar en base de datos:**
   ```sql
   SELECT * FROM users;
   SELECT * FROM customers;
   ```
5. **Cambiar `ddl-auto` de vuelta a `update`** después de verificar

## ⚠️ IMPORTANTE: Después de Probar

Una vez que confirmes que funciona correctamente, cambia en `application.properties`:

```properties
spring.jpa.hibernate.ddl-auto=update
```

## ✅ BENEFICIOS DE ESTA SOLUCIÓN

- ✅ Customer hereda correctamente de User
- ✅ El ID se comparte entre ambas tablas
- ✅ No hay duplicación de datos
- ✅ Las consultas son eficientes con JOIN
- ✅ Se mantiene la integridad referencial

## 📝 TESTING

### Crear un Customer:
```http
POST http://localhost:9000/api/customers
Content-Type: application/json

{
  "email": "test@example.com",
  "firstName": "Juan",
  "lastName": "Pérez",
  "shippingAddress": "Calle 123",
  "phone": "555-1234"
}
```

### Verificar en base de datos:
```sql
-- Debería mostrar el registro en users
SELECT * FROM users WHERE email = 'test@example.com';

-- Debería mostrar el registro en customers con el MISMO ID
SELECT * FROM customers c 
INNER JOIN users u ON c.id = u.id 
WHERE u.email = 'test@example.com';
```

---

**Estado:** ✅ Cambios aplicados y listos para probar
**Acción requerida:** Reiniciar aplicación Spring Boot

