# 🚀 QUICK START - CI/CD con GitHub Actions

## ⚡ Setup Rápido (5 minutos)

### 1️⃣ Crear Access Keys en AWS

```bash
# En AWS Console
IAM → Users → [Tu usuario] → Security credentials → Create access key
```

Guarda:
- ✅ Access Key ID
- ✅ Secret Access Key

---

### 2️⃣ Agregar Secrets en GitHub

```bash
# En tu repositorio de GitHub
Settings → Secrets and variables → Actions → New repository secret
```

Agregar:
- `AWS_ACCESS_KEY_ID` → Tu Access Key
- `AWS_SECRET_ACCESS_KEY` → Tu Secret Key

---

### 3️⃣ Crear Environment en AWS

```bash
cd back
eb create ecohouse-env --instance-type t2.small
```

---

### 4️⃣ Push y Deploy Automático

```bash
# Push a develop → Deploy a Staging
git checkout develop
git add .
git commit -m "Configurar CI/CD"
git push origin develop

# Push a main → Deploy a Production
git checkout main
git merge develop
git push origin main
```

---

## 🎯 Flujo Automático

```
┌─────────────────┐
│  Push to GitHub │
└────────┬────────┘
         │
         ▼
    ┌────────┐
    │ Tests  │
    └───┬────┘
        │
        ▼
    ┌────────┐
    │ Build  │
    └───┬────┘
        │
        ▼
    ┌────────┐
    │ Deploy │
    └────────┘
```

---

## 📊 Ver el Pipeline

1. Ve a tu repo en GitHub
2. Click en **"Actions"**
3. Verás el workflow ejecutándose

---

## ✅ Verificar Deploy

```bash
# URL de tu app
http://ecohouse-env.elasticbeanstalk.com

# Swagger
http://ecohouse-env.elasticbeanstalk.com/swagger-ui/index.html

# Health check
http://ecohouse-env.elasticbeanstalk.com/actuator/health
```

---

## 🔥 Comandos Útiles

```bash
# Ver logs del workflow
gh run list
gh run view

# Re-ejecutar workflow
gh run rerun

# Trigger manual
gh workflow run deploy-backend.yml
```

---

## 🎉 ¡Listo!

Cada push ahora desplegará automáticamente tu app a AWS.

**Documentación completa**: [CICD-GUIDE.md](.github/CICD-GUIDE.md)

