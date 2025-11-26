# 🏷️ Badges y Shields para README

## 📊 Badges Disponibles

### Estado del Pipeline

```markdown
![Deploy](https://github.com/TU-USUARIO/ecohouse/actions/workflows/deploy-backend.yml/badge.svg)
![Tests](https://github.com/TU-USUARIO/ecohouse/actions/workflows/ci-tests.yml/badge.svg)
![Docker](https://github.com/TU-USUARIO/ecohouse/actions/workflows/docker-deploy.yml/badge.svg)
```

### Tecnologías

```markdown
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![AWS](https://img.shields.io/badge/AWS-Elastic%20Beanstalk-orange.svg)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue.svg)
```

### Calidad de Código

```markdown
![Coverage](https://codecov.io/gh/TU-USUARIO/ecohouse/branch/main/graph/badge.svg)
![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=ecohouse&metric=alert_status)
![Security](https://snyk.io/test/github/TU-USUARIO/ecohouse/badge.svg)
```

### Licencia y Versión

```markdown
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)
```

---

## 🎨 Sección Completa para README

```markdown
# 🌱 EcoHouse Backend

<div align="center">

![Deploy](https://github.com/TU-USUARIO/ecohouse/actions/workflows/deploy-backend.yml/badge.svg)
![Tests](https://github.com/TU-USUARIO/ecohouse/actions/workflows/ci-tests.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)
![AWS](https://img.shields.io/badge/AWS-Elastic%20Beanstalk-orange.svg)

**API REST para gestión de reportes de impacto ambiental** 🌍

[Demo](http://ecohouse-env.elasticbeanstalk.com) · [Documentación](https://github.com/TU-USUARIO/ecohouse/wiki) · [Reportar Bug](https://github.com/TU-USUARIO/ecohouse/issues)

</div>

---

## ✨ Características

- 🌱 **Reportes Ambientales**: Generación automática de reportes de impacto
- 📊 **Métricas CO2**: Cálculo de huella de carbono y CO2 ahorrado
- 🏆 **Eco Points**: Sistema de gamificación para usuarios
- 📈 **Analytics**: Dashboard con tendencias y estadísticas
- 🔒 **Seguro**: Autenticación y autorización con Spring Security
- 🚀 **CI/CD**: Deploy automático con GitHub Actions
- 📖 **Documentado**: API completamente documentada con Swagger

---

## 🚀 Quick Start

### Ejecutar Localmente

\`\`\`bash
# Clonar repositorio
git clone https://github.com/TU-USUARIO/ecohouse.git
cd ecohouse/back

# Ejecutar
mvn spring-boot:run
\`\`\`

**Swagger UI**: http://localhost:9000/swagger-ui/index.html

### Deploy a AWS

\`\`\`bash
# Hacer push activa el deploy automático
git push origin main
\`\`\`

---

## 📚 Documentación

- [🚀 Guía de Deploy AWS](back/AWS-DEPLOY.md)
- [🔄 Pipeline CI/CD](.github/CICD-GUIDE.md)
- [📖 API Docs](http://localhost:9000/swagger-ui/index.html)
- [✅ Deployment Checklist](back/DEPLOYMENT-CHECKLIST.md)

---

## 🛠️ Stack Tecnológico

| Categoría | Tecnología |
|-----------|-----------|
| **Backend** | Java 21, Spring Boot 3.5.7 |
| **Base de Datos** | MySQL 8.0 |
| **Cloud** | AWS (EB, RDS, EC2, ECR, ECS) |
| **CI/CD** | GitHub Actions |
| **Containerización** | Docker |
| **Documentación** | Swagger/OpenAPI 3 |
| **Testing** | JUnit 5, Mockito |

---

## 📊 Estado del Proyecto

![Contributors](https://img.shields.io/github/contributors/TU-USUARIO/ecohouse)
![Commits](https://img.shields.io/github/commit-activity/m/TU-USUARIO/ecohouse)
![Issues](https://img.shields.io/github/issues/TU-USUARIO/ecohouse)
![PRs](https://img.shields.io/github/issues-pr/TU-USUARIO/ecohouse)

---

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Por favor lee nuestra [guía de contribución](CONTRIBUTING.md).

1. Fork el proyecto
2. Crea tu feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'feat: Add some AmazingFeature'`)
4. Push a la branch (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📄 Licencia

Distribuido bajo la licencia MIT. Ver `LICENSE` para más información.

---

## 👥 Equipo

**Backend Team** - [@TU-USUARIO](https://github.com/TU-USUARIO)

**Project Link**: [https://github.com/TU-USUARIO/ecohouse](https://github.com/TU-USUARIO/ecohouse)

---

<div align="center">

**Hecho con 💚 por el equipo de EcoHouse**

[⬆ Volver arriba](#-ecohouse-backend)

</div>
\`\`\`

---

## 🎨 Personalización

### Reemplazar valores:
1. **TU-USUARIO** → Tu username de GitHub
2. **ecohouse-env** → Tu environment de AWS EB
3. URLs de demo → Tus URLs reales

### Generar más badges:
- [Shields.io](https://shields.io/)
- [Badge Generator](https://badgen.net/)

### Agregar shields personalizados:

```markdown
![Custom](https://img.shields.io/badge/TEXTO-VALOR-COLOR.svg)

# Ejemplos:
![Uptime](https://img.shields.io/badge/uptime-99.9%25-brightgreen.svg)
![Response Time](https://img.shields.io/badge/response-150ms-green.svg)
![API Version](https://img.shields.io/badge/api-v1.0-blue.svg)
```

---

## 📊 Dashboard de Métricas (Opcional)

Si usas servicios adicionales:

```markdown
### Code Quality
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ecohouse&metric=alert_status)](https://sonarcloud.io/dashboard?id=ecohouse)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ecohouse&metric=coverage)](https://sonarcloud.io/dashboard?id=ecohouse)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=ecohouse&metric=bugs)](https://sonarcloud.io/dashboard?id=ecohouse)

### Security
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ecohouse&metric=security_rating)](https://sonarcloud.io/dashboard?id=ecohouse)
[![Known Vulnerabilities](https://snyk.io/test/github/TU-USUARIO/ecohouse/badge.svg)](https://snyk.io/test/github/TU-USUARIO/ecohouse)
```

---

## 🎯 Templates Adicionales

### Para Issues:

```markdown
**🐛 Bug Report**

**Describe el bug**
Una descripción clara del bug.

**Pasos para reproducir**
1. Ir a '...'
2. Click en '....'
3. Ver error

**Comportamiento esperado**
Descripción de lo que esperabas.

**Screenshots**
Si aplica, agregar screenshots.

**Ambiente:**
- OS: [e.g. Windows 11]
- Java: [e.g. 21]
- Browser: [e.g. Chrome 120]
```

### Para Pull Requests:

```markdown
**📝 Pull Request**

**Descripción**
Descripción clara de los cambios.

**Tipo de cambio**
- [ ] 🐛 Bug fix
- [ ] ✨ Nueva feature
- [ ] 💥 Breaking change
- [ ] 📝 Documentación

**Checklist**
- [ ] Código sigue el style guide
- [ ] Tests agregados/actualizados
- [ ] Documentación actualizada
- [ ] CI/CD pasa
```

---

**💡 Tip**: Mantén los badges actualizados y revisa que funcionen correctamente.

