# Script para Crear RDS MySQL para EcoHouse
# =====================================================

Write-Host "====================================" -ForegroundColor Cyan
Write-Host "  CREAR RDS MYSQL PARA ECOHOUSE" -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan
Write-Host ""

# Verificar que AWS CLI esté configurado
Write-Host "[1/5] Verificando AWS CLI..." -ForegroundColor Yellow
$awsIdentity = aws sts get-caller-identity | ConvertFrom-Json

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error: AWS CLI no configurado" -ForegroundColor Red
    exit 1
}

Write-Host "✓ Usuario AWS: $($awsIdentity.Arn)" -ForegroundColor Green
Write-Host ""

# Solicitar password
Write-Host "[2/5] Configuración de Base de Datos" -ForegroundColor Yellow
Write-Host ""
$dbPassword = Read-Host "Ingresa password para admin (mínimo 8 caracteres, incluye mayúsculas, minúsculas y números)"

if ($dbPassword.Length -lt 8) {
    Write-Host "❌ El password debe tener mínimo 8 caracteres" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[3/5] Creando instancia RDS MySQL..." -ForegroundColor Yellow
Write-Host "  - Identificador: ecohouse-db" -ForegroundColor Gray
Write-Host "  - Motor: MySQL 8.0.35" -ForegroundColor Gray
Write-Host "  - Clase: db.t3.micro (Free Tier elegible)" -ForegroundColor Gray
Write-Host "  - Storage: 20 GB" -ForegroundColor Gray
Write-Host "  - Usuario: admin" -ForegroundColor Gray
Write-Host "  - Base de datos: ecohouse_bd" -ForegroundColor Gray
Write-Host ""

aws rds create-db-instance `
    --db-instance-identifier ecohouse-db `
    --db-instance-class db.t3.micro `
    --engine mysql `
    --engine-version 8.0.35 `
    --master-username admin `
    --master-user-password $dbPassword `
    --allocated-storage 20 `
    --storage-type gp2 `
    --publicly-accessible `
    --db-name ecohouse_bd `
    --backup-retention-period 7 `
    --no-multi-az `
    --region us-east-1

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "❌ Error al crear RDS" -ForegroundColor Red
    Write-Host "Posibles causas:" -ForegroundColor Yellow
    Write-Host "  - Ya existe una instancia con ese nombre" -ForegroundColor Gray
    Write-Host "  - Permisos insuficientes" -ForegroundColor Gray
    Write-Host "  - Password no cumple requisitos" -ForegroundColor Gray
    exit 1
}

Write-Host ""
Write-Host "✓ RDS creado exitosamente!" -ForegroundColor Green
Write-Host ""

Write-Host "[4/5] Esperando que RDS esté disponible..." -ForegroundColor Yellow
Write-Host "Esto puede tomar 5-10 minutos. Por favor espera..." -ForegroundColor Gray
Write-Host ""

$attempt = 0
$maxAttempts = 30

do {
    $attempt++
    Start-Sleep -Seconds 30

    $status = aws rds describe-db-instances `
        --db-instance-identifier ecohouse-db `
        --query 'DBInstances[0].DBInstanceStatus' `
        --output text 2>$null

    $elapsed = $attempt * 30
    $minutes = [math]::Floor($elapsed / 60)
    $seconds = $elapsed % 60

    Write-Host "[$minutes min $seconds seg] Estado: $status" -ForegroundColor Gray

    if ($attempt -ge $maxAttempts) {
        Write-Host ""
        Write-Host "⚠️  El proceso está tomando más tiempo del esperado" -ForegroundColor Yellow
        Write-Host "Puedes verificar el estado en AWS Console → RDS" -ForegroundColor Gray
        break
    }

} while ($status -ne "available")

if ($status -eq "available") {
    Write-Host ""
    Write-Host "✓ RDS disponible!" -ForegroundColor Green
    Write-Host ""

    # Obtener endpoint
    Write-Host "[5/5] Configurando variables de entorno..." -ForegroundColor Yellow

    $endpoint = aws rds describe-db-instances `
        --db-instance-identifier ecohouse-db `
        --query 'DBInstances[0].Endpoint.Address' `
        --output text

    $port = aws rds describe-db-instances `
        --db-instance-identifier ecohouse-db `
        --query 'DBInstances[0].Endpoint.Port' `
        --output text

    Write-Host ""
    Write-Host "Información de la Base de Datos:" -ForegroundColor Cyan
    Write-Host "  ├─ Endpoint: $endpoint" -ForegroundColor White
    Write-Host "  ├─ Puerto: $port" -ForegroundColor White
    Write-Host "  ├─ Usuario: admin" -ForegroundColor White
    Write-Host "  ├─ Base de datos: ecohouse_bd" -ForegroundColor White
    Write-Host "  └─ Password: [guardado en variables EB]" -ForegroundColor White
    Write-Host ""

    # Configurar en Elastic Beanstalk
    $jdbcUrl = "jdbc:mysql://${endpoint}:${port}/ecohouse_bd?useSSL=true&serverTimezone=UTC"

    Write-Host "Configurando en Elastic Beanstalk..." -ForegroundColor Yellow

    C:\Users\isabe\AppData\Roaming\Python\Python313\Scripts\eb.exe setenv `
        MYSQL_URL="$jdbcUrl" `
        MYSQL_USER="admin" `
        MYSQL_PASS="$dbPassword"

    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "====================================" -ForegroundColor Green
        Write-Host "  ✓ CONFIGURACIÓN COMPLETADA" -ForegroundColor Green
        Write-Host "====================================" -ForegroundColor Green
        Write-Host ""

        Write-Host "Próximos pasos:" -ForegroundColor Cyan
        Write-Host "  1. Configurar Security Groups para permitir conexión desde EB" -ForegroundColor White
        Write-Host "  2. Desplegar la aplicación" -ForegroundColor White
        Write-Host ""

        Write-Host "Comandos útiles:" -ForegroundColor Cyan
        Write-Host "  # Ver variables de entorno" -ForegroundColor Gray
        Write-Host "  C:\Users\isabe\AppData\Roaming\Python\Python313\Scripts\eb.exe printenv" -ForegroundColor White
        Write-Host ""
        Write-Host "  # Desplegar aplicación" -ForegroundColor Gray
        Write-Host "  C:\Users\isabe\AppData\Roaming\Python\Python313\Scripts\eb.exe deploy" -ForegroundColor White
        Write-Host ""

        Write-Host "📚 Guía completa: DATABASE-SETUP.md" -ForegroundColor Cyan
        Write-Host ""

        # Guardar información en archivo
        $info = @"
# Información de RDS MySQL - EcoHouse
Creado: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")

## Detalles de Conexión
- Endpoint: $endpoint
- Puerto: $port
- Usuario: admin
- Base de datos: ecohouse_bd
- JDBC URL: $jdbcUrl

## Security Group
⚠️ IMPORTANTE: Debes configurar el security group de RDS para permitir conexiones desde el ambiente EB.

Pasos:
1. Ve a AWS Console → RDS → Databases → ecohouse-db
2. Click en "Connectivity & security"
3. Click en el Security Group
4. Edit inbound rules → Add rule
5. Type: MySQL/Aurora (port 3306)
6. Source: Security group del ambiente EB
7. Save rules

## Comandos Útiles

### Ver estado de RDS
aws rds describe-db-instances --db-instance-identifier ecohouse-db

### Crear snapshot
aws rds create-db-snapshot --db-instance-identifier ecohouse-db --db-snapshot-identifier ecohouse-backup-$(Get-Date -Format "yyyyMMdd")

### Ver snapshots
aws rds describe-db-snapshots --db-instance-identifier ecohouse-db

### Eliminar RDS (CUIDADO!)
aws rds delete-db-instance --db-instance-identifier ecohouse-db --skip-final-snapshot
"@

        $info | Out-File -FilePath "RDS-INFO.txt" -Encoding UTF8
        Write-Host "✓ Información guardada en: RDS-INFO.txt" -ForegroundColor Green
        Write-Host ""

    } else {
        Write-Host ""
        Write-Host "⚠️  No se pudieron configurar las variables en EB" -ForegroundColor Yellow
        Write-Host "Configúralas manualmente:" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "C:\Users\isabe\AppData\Roaming\Python\Python313\Scripts\eb.exe setenv MYSQL_URL=`"$jdbcUrl`" MYSQL_USER=`"admin`" MYSQL_PASS=`"$dbPassword`"" -ForegroundColor White
        Write-Host ""
    }

} else {
    Write-Host ""
    Write-Host "⚠️  RDS aún no está disponible" -ForegroundColor Yellow
    Write-Host "Verifica el estado en:" -ForegroundColor Gray
    Write-Host "  https://console.aws.amazon.com/rds/" -ForegroundColor White
    Write-Host ""
}

Write-Host "¡Listo! 🚀" -ForegroundColor Green

