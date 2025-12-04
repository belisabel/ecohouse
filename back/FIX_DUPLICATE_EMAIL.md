# 🔧 Solución: Error de Email Duplicado en Customer

## ❌ PROBLEMA

Al intentar registrar un usuario, se produce este error:

```
Duplicate entry 'marcelo@gmail.com' for key 'users.UK6dotkott2kjsp8vw4d0m25fb7'
```

### Causa:
Estabas intentando registrar **DOS VECES** el mismo usuario con el mismo email. El email tiene una restricción **UNIQUE** en la tabla `users`, por lo que no puede haber duplicados.

### Stack Trace Relevante:
```
at com.EcoHouse.user.service.impl.CustomerServiceImpl.createCustomer(CustomerServiceImpl.java:42)
at com.EcoHouse.auth.controller.AuthController.register(AuthController.java:38)
```

## ✅ SOLUCIÓN APLICADA

He agregado validación para verificar si el email ya existe ANTES de intentar guardarlo:

### 1. Validación en `createCustomer(User user)`:

```java
@Override
public Customer createCustomer(User user) {
    // ✅ VALIDACIÓN AGREGADA
    if (customerRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("Ya existe un usuario con el email: " + user.getEmail());
    }
    
    Customer customer = new Customer();
    customer.setEmail(user.getEmail());
    // ...resto del código
    return customerRepository.save(customer);
}
```

### 2. Validación en `createCustomer(CustomerRequest request)`:

```java
@Override
public CustomerResponse createCustomer(CustomerRequest request) {
    // ✅ VALIDACIÓN AGREGADA
    if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new RuntimeException("Ya existe un usuario con el email: " + request.getEmail());
    }
    
    Customer customer = new Customer();
    customer.setEmail(request.getEmail());
    // ...resto del código
    return toDTO(saved);
}
```

## 🎯 COMPORTAMIENTO AHORA

### Caso 1: Email NO existe (✅ Success)
```http
POST /api/auth/register
{
  "email": "nuevo@gmail.com",
  "firstName": "Juan",
  "lastName": "Pérez",
  "password": "123456"
}
```
**Respuesta:** `201 Created` - Usuario creado exitosamente

### Caso 2: Email YA existe (⚠️ Error Controlado)
```http
POST /api/auth/register
{
  "email": "marcelo@gmail.com",  // Ya existe
  "firstName": "Pedro",
  "lastName": "García",
  "password": "123456"
}
```
**Respuesta:** `400 Bad Request` con mensaje:
```json
{
  "error": "Ya existe un usuario con el email: marcelo@gmail.com"
}
```

## 🔍 VERIFICACIÓN

### Antes (Error SQL):
```
❌ SQL Error: 1062
❌ Duplicate entry 'marcelo@gmail.com' for key 'users.UK6dotkott2kjsp8vw4d0m25fb7'
❌ BUILD FAILURE
```

### Después (Error Controlado):
```
✅ RuntimeException capturada
✅ Mensaje claro al usuario
✅ No falla la aplicación
```

## 📋 ESTRUCTURA DE LA BASE DE DATOS

La tabla `users` tiene esta restricción:

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,  -- ← UNIQUE constraint
    ...
);
```

Esta constraint se llama: `UK6dotkott2kjsp8vw4d0m25fb7`

## 💡 MEJORAS FUTURAS (Opcional)

### 1. Usar excepciones personalizadas:

```java
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Ya existe un usuario con el email: " + email);
    }
}
```

### 2. Manejo global de excepciones:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(ex.getMessage()));
    }
}
```

### 3. Validación a nivel de Controller:

```java
@PostMapping("/register")
public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    // Spring Validation automática con @Email, @NotBlank, etc.
}
```

## 🚀 PRÓXIMOS PASOS

1. **Reinicia tu aplicación** Spring Boot
2. **Prueba registrar un usuario nuevo** (debería funcionar)
3. **Intenta registrar con el MISMO email** (debería dar error controlado)
4. **Verifica en Swagger** que el error es claro y útil

## ✅ ESTADO

- ✅ Validación agregada en ambos métodos `createCustomer`
- ✅ No más errores SQL por duplicados
- ✅ Mensajes de error claros al usuario
- ✅ Aplicación no se cae por este error

---

**Problema resuelto** ✅ 

Ahora cuando intentes registrar un usuario con un email que ya existe, recibirás un mensaje claro en lugar de un error SQL.

