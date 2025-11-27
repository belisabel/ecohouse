# Script de Despliegue Completo EcoHouse en AWS Elastic Beanstalk
# =====================================================================

Write-Host "====================================" -ForegroundColor Cyan
Write-Host "  DESPLIEGUE ECOHOUSE EN AWS" -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan
Write-Host ""

# Configuración
$EB_PATH = "C:\Users\isabe\AppData\Roaming\Python\Python313\Scripts\eb.exe"
$PROJECT_PATH = "C:\Users\isabe\Documents\NoCountry_E_commerce\proyectoP\eco\ecohouse\back"

# Cambiar al directorio del proyecto
Set-Location $PROJECT_PATH

Write-Host "[1/5] Compilando aplicación..." -ForegroundColor Yellow
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error en la compilación. Abortando." -ForegroundColor Red
    exit 1
}
Write-Host "✓ Compilación exitosa" -ForegroundColor Green
Write-Host ""

Write-Host "[2/5] Verificando usuario AWS..." -ForegroundColor Yellow
aws sts get-caller-identity
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error: No hay usuario AWS configurado." -ForegroundColor Red
    exit 1
}
Write-Host "✓ Usuario AWS verificado" -ForegroundColor Green
Write-Host ""

Write-Host "[3/5] Verificando aplicación Elastic Beanstalk..." -ForegroundColor Yellow
& $EB_PATH status
$statusCode = $LASTEXITCODE

if ($statusCode -ne 0) {
    Write-Host ""
    Write-Host "No hay ambiente creado. Opciones:" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "  A) Crear ambiente sin base de datos (RECOMENDADO)" -ForegroundColor Cyan
    Write-Host "     - Más flexible y escalable" -ForegroundColor Gray
    Write-Host "     - Puedes usar RDS separado" -ForegroundColor Gray
    Write-Host ""
    Write-Host "  B) Crear ambiente con base de datos integrada" -ForegroundColor Cyan
    Write-Host "     - Menos flexible pero más rápido de configurar" -ForegroundColor Gray
    Write-Host "     - Base de datos vinculada al ambiente" -ForegroundColor Gray
    Write-Host ""

    $opcion = Read-Host "Selecciona opción (A/B)"

    Write-Host ""
    Write-Host "[4/5] Creando ambiente en AWS..." -ForegroundColor Yellow

    if ($opcion -eq "B" -or $opcion -eq "b") {
        Write-Host "Creando ambiente CON base de datos MySQL integrada..." -ForegroundColor Cyan
        & $EB_PATH create ecohouse-env --instance-type t3.small --database --database.engine mysql --database.username admin
    } else {
        Write-Host "Creando ambiente SIN base de datos (tendrás que configurar RDS después)..." -ForegroundColor Cyan
        & $EB_PATH create ecohouse-env --instance-type t3.small
    }

    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error al crear el ambiente." -ForegroundColor Red
        exit 1
    }
    Write-Host "✓ Ambiente creado exitosamente" -ForegroundColor Green
    Write-Host ""

    Write-Host "Esperando 10 segundos para que el ambiente se estabilice..." -ForegroundColor Yellow
    Start-Sleep -Seconds 10
} else {
    Write-Host "✓ Ambiente ya existe" -ForegroundColor Green
    Write-Host ""
}

Write-Host "[5/5] Desplegando aplicación..." -ForegroundColor Yellow
& $EB_PATH deploy
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error en el despliegue." -ForegroundColor Red
    exit 1
}
Write-Host "✓ Despliegue exitoso" -ForegroundColor Green
Write-Host ""

Write-Host "====================================" -ForegroundColor Green
Write-Host "  DESPLIEGUE COMPLETADO" -ForegroundColor Green
Write-Host "====================================" -ForegroundColor Green
Write-Host ""

Write-Host "Comandos útiles:" -ForegroundColor Cyan
Write-Host "  - Ver estado:    $EB_PATH status" -ForegroundColor White
Write-Host "  - Ver logs:      $EB_PATH logs" -ForegroundColor White
Write-Host "  - Abrir app:     $EB_PATH open" -ForegroundColor White
Write-Host "  - Ver variables: $EB_PATH printenv" -ForegroundColor White
Write-Host ""

Write-Host "Abriendo aplicación en el navegador..." -ForegroundColor Yellow
Start-Sleep -Seconds 2
& $EB_PATH open

