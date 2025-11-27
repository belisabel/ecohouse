# 🔐 Script para Configurar GitHub Secrets

Write-Host "====================================" -ForegroundColor Cyan
Write-Host "  CONFIGURAR GITHUB SECRETS" -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Este script te ayudará a configurar los secrets necesarios en GitHub" -ForegroundColor Yellow
Write-Host ""

# Obtener información de AWS
Write-Host "[1/3] Obteniendo información de AWS..." -ForegroundColor Yellow
$awsIdentity = aws sts get-caller-identity | ConvertFrom-Json

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Usuario AWS identificado:" -ForegroundColor Green
    Write-Host "  - Account: $($awsIdentity.Account)" -ForegroundColor White
    Write-Host "  - User: $($awsIdentity.Arn)" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host "❌ Error: No se pudo identificar el usuario AWS" -ForegroundColor Red
    exit 1
}

# Información sobre GitHub Secrets
Write-Host "[2/3] Instrucciones para configurar GitHub Secrets:" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. Ve a tu repositorio en GitHub" -ForegroundColor White
Write-Host "2. Click en 'Settings' (Configuración)" -ForegroundColor White
Write-Host "3. En el menú lateral, click en 'Secrets and variables' → 'Actions'" -ForegroundColor White
Write-Host "4. Click en 'New repository secret'" -ForegroundColor White
Write-Host ""

Write-Host "Necesitas agregar los siguientes secrets:" -ForegroundColor Cyan
Write-Host ""

# Obtener Access Key del archivo de configuración de AWS
$awsConfigPath = "$env:USERPROFILE\.aws\credentials"

if (Test-Path $awsConfigPath) {
    Write-Host "  Secret 1:" -ForegroundColor Yellow
    Write-Host "  ├─ Name:  AWS_ACCESS_KEY_ID" -ForegroundColor White
    Write-Host "  └─ Value: [Encontrado en tu configuración de AWS]" -ForegroundColor Gray
    Write-Host ""

    Write-Host "  Secret 2:" -ForegroundColor Yellow
    Write-Host "  ├─ Name:  AWS_SECRET_ACCESS_KEY" -ForegroundColor White
    Write-Host "  └─ Value: [Encontrado en tu configuración de AWS]" -ForegroundColor Gray
    Write-Host ""

    Write-Host "⚠️  IMPORTANTE: Por seguridad, NO mostramos las keys aquí." -ForegroundColor Yellow
    Write-Host "    Las keys están en: $awsConfigPath" -ForegroundColor Gray
    Write-Host ""

    $showKeys = Read-Host "¿Quieres abrir el archivo de configuración de AWS? (s/n)"
    if ($showKeys -eq "s" -or $showKeys -eq "S") {
        notepad $awsConfigPath
    }
} else {
    Write-Host "❌ No se encontró el archivo de configuración de AWS" -ForegroundColor Red
    Write-Host "   Busca tus credenciales en AWS Console → IAM → Users → Security credentials" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "[3/3] Verificación:" -ForegroundColor Yellow
Write-Host ""
Write-Host "Una vez que hayas configurado los secrets en GitHub:" -ForegroundColor White
Write-Host "  1. Haz un commit y push de tus cambios" -ForegroundColor Gray
Write-Host "  2. Ve a GitHub → Actions" -ForegroundColor Gray
Write-Host "  3. Verifica que el pipeline se ejecute correctamente" -ForegroundColor Gray
Write-Host ""

Write-Host "====================================" -ForegroundColor Green
Write-Host "  CONFIGURACIÓN COMPLETADA" -ForegroundColor Green
Write-Host "====================================" -ForegroundColor Green
Write-Host ""

Write-Host "📚 Guía completa: CICD-SETUP-GUIDE.md" -ForegroundColor Cyan
Write-Host ""

# Preguntar si quiere crear el ambiente ahora
$createEnv = Read-Host "¿Quieres crear el ambiente en Elastic Beanstalk ahora? (s/n)"
if ($createEnv -eq "s" -or $createEnv -eq "S") {
    Write-Host ""
    Write-Host "Creando ambiente en AWS Elastic Beanstalk..." -ForegroundColor Yellow

    $EB_PATH = "C:\Users\isabe\AppData\Roaming\Python\Python313\Scripts\eb.exe"
    $PROJECT_PATH = "C:\Users\isabe\Documents\NoCountry_E_commerce\proyectoP\eco\ecohouse\back"

    Set-Location $PROJECT_PATH

    Write-Host ""
    Write-Host "Opciones:" -ForegroundColor Cyan
    Write-Host "  A) Crear ambiente sin base de datos (RECOMENDADO)" -ForegroundColor White
    Write-Host "  B) Crear ambiente con base de datos MySQL integrada" -ForegroundColor White
    Write-Host ""

    $opcion = Read-Host "Selecciona opción (A/B)"

    if ($opcion -eq "B" -or $opcion -eq "b") {
        Write-Host ""
        Write-Host "Creando ambiente CON base de datos..." -ForegroundColor Yellow
        & $EB_PATH create ecohouse-env --instance-type t3.small --database --database.engine mysql --database.username admin
    } else {
        Write-Host ""
        Write-Host "Creando ambiente SIN base de datos..." -ForegroundColor Yellow
        & $EB_PATH create ecohouse-env --instance-type t3.small
    }

    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "✅ Ambiente creado exitosamente!" -ForegroundColor Green
        Write-Host ""
        Write-Host "Ahora puedes hacer push a GitHub y el pipeline desplegará automáticamente." -ForegroundColor Cyan
    } else {
        Write-Host ""
        Write-Host "❌ Error al crear el ambiente" -ForegroundColor Red
        Write-Host "   Revisa los logs para más detalles" -ForegroundColor Yellow
    }
} else {
    Write-Host ""
    Write-Host "Puedes crear el ambiente más tarde ejecutando:" -ForegroundColor Yellow
    Write-Host "  .\deploy-aws.ps1" -ForegroundColor White
    Write-Host ""
}

Write-Host "¡Listo! 🚀" -ForegroundColor Green

