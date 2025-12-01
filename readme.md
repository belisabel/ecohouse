# EcoHouse

Ecologic E-commerce application.

## Resolución de Conflictos en Git (Merge Conflicts)

Cuando trabajas con Git y múltiples ramas, pueden surgir **conflictos de fusión (merge conflicts)**. Estos ocurren cuando Git no puede determinar automáticamente qué cambios conservar porque dos personas modificaron las mismas líneas de código.

### ¿Qué significan las opciones de resolución de conflictos?

Cuando ves un conflicto en VS Code u otro editor, aparecen estas opciones:

| Opción | En español | Descripción |
|--------|------------|-------------|
| **Accept Current Change** | Aceptar cambio actual | Mantiene los cambios de **tu rama actual** (la rama donde estás trabajando, por ejemplo `main` o tu rama local). Descarta los cambios entrantes. |
| **Accept Incoming Change** | Aceptar cambio entrante | Mantiene los cambios de la **otra rama** (la rama que estás intentando fusionar). Descarta tus cambios actuales. |
| **Accept Both Changes** | Aceptar ambos cambios | Conserva **ambos** cambios, uno después del otro. |
| **Compare Changes** | Comparar cambios | Abre una vista de comparación para ver las diferencias lado a lado. |

### Ejemplo visual de un conflicto

```
<<<<<<< HEAD (Current Change)
// Tu código actual en tu rama
String mensaje = "Hola Mundo";
=======
// Código de la otra rama (Incoming Change)
String mensaje = "Hello World";
>>>>>>> otra-rama (Incoming Change)
```

### ¿Cuál elegir?

- **Accept Current Change**: Cuando quieres conservar TU versión del código y descartar la del otro desarrollador.
- **Accept Incoming Change**: Cuando el código de la otra rama es mejor o más actualizado.
- **Accept Both Changes**: Cuando ambas modificaciones son necesarias y no se contradicen.

### Recomendaciones

1. Siempre **revisa los cambios** antes de aceptar una opción
2. Comunícate con tu equipo si no estás seguro
3. Después de resolver, **prueba tu código** para asegurarte de que funciona correctamente

---

## Project Structure

- `back/` - Backend API (Java Spring Boot)
- `.aws/` - AWS configuration files
- `.github/` - GitHub Actions workflows
