# 🚀 CI/CD Pipeline - GitHub Actions + AWS

## 📋 Descripción

Este proyecto incluye pipelines automatizados de CI/CD usando GitHub Actions para:
- ✅ Ejecutar tests automáticamente
- 🔍 Análisis de calidad de código
- 🔒 Escaneo de seguridad
- 🏗️ Build automático
- 🚀 Deploy automático a AWS (Elastic Beanstalk o ECS)

---

## 📁 Workflows Incluidos

### 1. **deploy-backend.yml** - Deploy Automático a AWS EB
Deploy automático a Elastic Beanstalk:
- ✅ Tests
- 🏗️ Build Maven
- 🚀 Deploy a Staging (branch `develop`)
- 🌟 Deploy a Production (branch `main`)

### 2. **docker-deploy.yml** - Deploy con Docker a ECS
Build y push de imagen Docker a AWS ECR:
- 🐳 Build Docker image
- 📤 Push a Amazon ECR
- 🔒 Escaneo de vulnerabilidades
- 🚀 Deploy a ECS/Fargate

### 3. **ci-tests.yml** - CI/Tests Continuos
Integración continua con tests y análisis:
- 🧪 Unit tests
- 📊 Coverage report
- 🔍 SonarCloud analysis
- 🔒 Security scan (Snyk, OWASP)
- 🎨 Code style check

---

## 🔧 Configuración Inicial

### 1️⃣ Configurar Secrets en GitHub

Ve a tu repositorio → **Settings** → **Secrets and variables** → **Actions** → **New repository secret**

Agrega los siguientes secrets:

```
AWS_ACCESS_KEY_ID          → Tu Access Key de AWS IAM
AWS_SECRET_ACCESS_KEY      → Tu Secret Key de AWS IAM
SONAR_TOKEN               → Token de SonarCloud (opcional)
SNYK_TOKEN                → Token de Snyk (opcional)
```

#### Cómo obtener AWS Keys:

1. Ve a **AWS Console** → **IAM** → **Users**
2. Selecciona tu usuario (o crea uno nuevo)
3. **Security credentials** → **Create access key**
4. Selecciona "Application running outside AWS"
5. Copia el **Access Key ID** y **Secret Access Key**

#### Permisos necesarios para el usuario IAM:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "elasticbeanstalk:*",
        "s3:*",
        "ec2:*",
        "cloudformation:*",
        "autoscaling:*",
        "elasticloadbalancing:*",
        "rds:*",
        "ecr:*",
        "ecs:*",
        "logs:*"
      ],
      "Resource": "*"
    }
  ]
}
```

O usa la política administrada: **`AdministratorAccess-AWSElasticBeanstalk`**

---

### 2️⃣ Configurar Variables de Entorno

Edita `.github/workflows/deploy-backend.yml` y actualiza:

```yaml
env:
  AWS_REGION: us-east-1                    # Tu región de AWS
  EB_APPLICATION_NAME: ecohouse-api        # Nombre de tu app en EB
  EB_ENVIRONMENT_NAME: ecohouse-env        # Nombre de tu environment en EB
  JAVA_VERSION: '21'
```

---

### 3️⃣ Crear Ambientes en AWS

#### Opción A: Elastic Beanstalk (Recomendado)

```bash
# Production
eb create ecohouse-env --instance-type t2.small

# Staging
eb create ecohouse-staging --instance-type t2.micro
```

#### Opción B: ECS/Fargate

1. Crear cluster:
```bash
aws ecs create-cluster --cluster-name ecohouse-cluster
```

2. Crear repositorio ECR:
```bash
aws ecr create-repository --repository-name ecohouse-backend
```

3. Actualizar `.aws/task-definition.json` con tu Account ID

4. Crear servicio ECS:
```bash
aws ecs create-service \
  --cluster ecohouse-cluster \
  --service-name ecohouse-service \
  --task-definition ecohouse-task \
  --desired-count 1 \
  --launch-type FARGATE
```

---

### 4️⃣ Configurar GitHub Environments (Opcional pero Recomendado)

1. Ve a **Settings** → **Environments**
2. Crea dos environments: `staging` y `production`
3. En `production`, habilita **"Required reviewers"** (para aprobar deploys)

---

## 🚀 Cómo Funciona

### Flujo de Deploy a Elastic Beanstalk:

```mermaid
Push to branch → Tests → Build → Deploy → Verify
```

#### Branch `develop` → Staging:
```bash
git add .
git commit -m "Nueva feature"
git push origin develop
```
→ Deploy automático a **ecohouse-staging**

#### Branch `main` → Production:
```bash
git checkout main
git merge develop
git push origin main
```
→ Deploy automático a **ecohouse-env** (production)

---

### Flujo de Docker + ECS:

```mermaid
Push → Build Docker → Push to ECR → Deploy to ECS
```

---

## 📊 Monitoreo del Pipeline

### Ver el estado:
1. Ve a tu repositorio en GitHub
2. Click en **"Actions"**
3. Verás todos los workflows ejecutándose

### Badges para README:
Agrega estos badges a tu README.md:

```markdown
![Deploy Status](https://github.com/tu-usuario/ecohouse/actions/workflows/deploy-backend.yml/badge.svg)
![CI Status](https://github.com/tu-usuario/ecohouse/actions/workflows/ci-tests.yml/badge.svg)
```

---

## 🔍 Configuraciones Adicionales

### SonarCloud (Análisis de Código)

1. Ve a https://sonarcloud.io
2. Conecta tu repositorio de GitHub
3. Copia el token
4. Agrégalo como secret: `SONAR_TOKEN`
5. Actualiza el workflow con tu organization:
```yaml
-Dsonar.organization=tu-organizacion
```

### Snyk (Seguridad)

1. Ve a https://snyk.io
2. Conecta tu repositorio
3. Copia el token
4. Agrégalo como secret: `SNYK_TOKEN`

---

## 🎯 Mejores Prácticas

### Branch Strategy:

```
main          → Production (requiere aprobación)
  ↑
develop       → Staging (deploy automático)
  ↑
feature/*     → Tests y CI (no deploy)
```

### Commits:

```bash
# ✅ Bueno
git commit -m "feat: agregar endpoint de reportes"
git commit -m "fix: corregir cálculo de CO2"
git commit -m "docs: actualizar README"

# ❌ Malo
git commit -m "cambios"
git commit -m "fix"
```

### Pull Requests:

1. Crea una feature branch: `git checkout -b feature/nueva-funcionalidad`
2. Haz tus cambios y commits
3. Push: `git push origin feature/nueva-funcionalidad`
4. Crea un Pull Request a `develop`
5. El pipeline correrá automáticamente
6. Una vez aprobado, se mergea

---

## 🐛 Troubleshooting

### Error: "AWS credentials not configured"
- Verifica que los secrets `AWS_ACCESS_KEY_ID` y `AWS_SECRET_ACCESS_KEY` estén configurados
- Verifica que el usuario IAM tenga los permisos necesarios

### Error: "EB environment not found"
- Crea el environment primero: `eb create ecohouse-env`
- Verifica que el nombre en el workflow coincida

### Error: "Tests failed"
- Verifica que los tests pasen localmente: `mvn test`
- Revisa los logs en GitHub Actions para ver qué test falló

### Deploy demora mucho
- El primer deploy puede tardar 10-15 minutos
- Los siguientes son más rápidos (3-5 minutos)

---

## 📝 Comandos Útiles

### Ver logs del workflow:
```bash
gh run list
gh run view <run-id>
gh run watch
```

### Re-ejecutar workflow fallido:
```bash
gh run rerun <run-id>
```

### Trigger manual:
```bash
gh workflow run deploy-backend.yml
```

---

## 🔒 Seguridad

### Secrets Management:

Para producción, considera usar **AWS Secrets Manager**:

```yaml
- name: Get secrets from AWS
  run: |
    export MYSQL_PASS=$(aws secretsmanager get-secret-value \
      --secret-id ecohouse/mysql/password \
      --query SecretString --output text)
```

### Rotate Access Keys:

Rotar las keys cada 90 días:
```bash
aws iam create-access-key --user-name ecohouse-deployer
# Actualizar secrets en GitHub
aws iam delete-access-key --access-key-id OLD_KEY_ID --user-name ecohouse-deployer
```

---

## 📈 Métricas y Dashboards

### GitHub Insights:
- **Actions** → Ver historial de workflows
- **Insights** → Ver estadísticas del proyecto

### AWS CloudWatch:
- Crear dashboard con métricas de EB/ECS
- Configurar alarmas para errores

---

## 🎓 Recursos Adicionales

- [GitHub Actions Docs](https://docs.github.com/en/actions)
- [AWS EB Deployment](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-getting-started.html)
- [Docker Multi-stage Builds](https://docs.docker.com/build/building/multi-stage/)
- [SonarCloud](https://sonarcloud.io/documentation)

---

## 🎉 ¡Listo!

Tu pipeline CI/CD está configurado. Cada push activará automáticamente:

✅ Tests  
✅ Build  
✅ Security scans  
✅ Deploy (según la branch)  

**¡Disfruta del deploy automático! 🚀**

