# Script para cargar y verificar las órdenes de ejemplo
# Ejecuta este script después de iniciar la aplicación Spring Boot

Write-Host "🚀 Iniciando carga de órdenes de ejemplo..." -ForegroundColor Green
Write-Host ""

# Paso 1: Cargar las órdenes de ejemplo
Write-Host "📦 Paso 1: Cargando 10 órdenes de ejemplo..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/orders/load-sample-orders" -Method Post
    Write-Host "✅ Órdenes cargadas exitosamente!" -ForegroundColor Green
    Write-Host "   Cantidad: $($response.count)" -ForegroundColor Cyan
    Write-Host "   Números de orden:" -ForegroundColor Cyan
    $response.orderNumbers | ForEach-Object { Write-Host "   - $_" -ForegroundColor White }
} catch {
    Write-Host "❌ Error al cargar órdenes: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "   Asegúrate de que la aplicación esté ejecutándose en http://localhost:8080" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Start-Sleep -Seconds 2

# Paso 2: Verificar total de órdenes
Write-Host "🔍 Paso 2: Verificando total de órdenes..." -ForegroundColor Yellow
try {
    $orders = Invoke-RestMethod -Uri "http://localhost:8080/api/orders?page=0&size=100" -Method Get
    Write-Host "✅ Total de órdenes en el sistema: $($orders.totalElements)" -ForegroundColor Green
    Write-Host "   Páginas disponibles: $($orders.totalPages)" -ForegroundColor Cyan
    Write-Host "   Órdenes en esta página: $($orders.numberOfElements)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Error al obtener órdenes: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Start-Sleep -Seconds 1

# Paso 3: Obtener estadísticas de ventas
Write-Host "📊 Paso 3: Obteniendo estadísticas de ventas..." -ForegroundColor Yellow
try {
    $stats = Invoke-RestMethod -Uri "http://localhost:8080/api/sales/statistics" -Method Get
    Write-Host "✅ Estadísticas actualizadas:" -ForegroundColor Green
    Write-Host "   💰 Total de ventas: €$($stats.totalSales)" -ForegroundColor Cyan
    Write-Host "   📦 Total de órdenes: $($stats.totalOrders)" -ForegroundColor Cyan
    Write-Host "   📈 Valor promedio: €$($stats.averageOrderValue)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Error al obtener estadísticas: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "🎉 ¡Proceso completado!" -ForegroundColor Green
Write-Host ""
Write-Host "💡 Comandos útiles:" -ForegroundColor Yellow
Write-Host "   - Ver todas las órdenes: curl 'http://localhost:8080/api/orders?page=0&size=20'" -ForegroundColor White
Write-Host "   - Ver estadísticas: curl 'http://localhost:8080/api/sales/statistics'" -ForegroundColor White
Write-Host "   - Eliminar órdenes de ejemplo: curl -X DELETE 'http://localhost:8080/api/admin/orders/delete-sample-orders'" -ForegroundColor White
Write-Host ""

