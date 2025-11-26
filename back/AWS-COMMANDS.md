# 🎯 Comandos AWS - Referencia Rápida

## 🔧 Configuración Inicial

```bash
# Configurar AWS CLI
aws configure

# Verificar configuración
aws sts get-caller-identity

# Instalar EB CLI
pip install awsebcli --upgrade
```

---

## 🚀 Elastic Beanstalk

### Inicialización
```bash
# Inicializar proyecto
eb init -p corretto-21 ecohouse-api --region us-east-1

# Iniciar wizard interactivo
eb init
```

### Ambientes
```bash
# Crear ambiente
eb create ecohouse-env --instance-type t2.small

# Crear con base de datos (no recomendado, mejor RDS separado)
eb create ecohouse-env --database

# Listar ambientes
eb list

# Cambiar ambiente activo
eb use ecohouse-env
```

### Despliegue
```bash
# Deploy actual
eb deploy

# Deploy con mensajes
eb deploy -m "Agregando nuevo feature"

# Deploy y abrir
eb deploy && eb open
```

### Información
```bash
# Ver estado
eb status

# Ver información detallada
eb status --verbose

# Ver configuración
eb config

# Ver variables de entorno
eb printenv
```

### Logs
```bash
# Ver últimos logs
eb logs

# Streaming en tiempo real
eb logs --stream

# Logs de un log específico
eb logs --log-group /aws/elasticbeanstalk/ecohouse-env/var/log/eb-engine.log

# Guardar logs en archivo
eb logs > logs.txt
```

### SSH
```bash
# Conectar por SSH
eb ssh

# Ejecutar comando remoto
eb ssh --command "ls -la /var/app"
```

### Escalamiento
```bash
# Cambiar número de instancias
eb scale 2

# Ver configuración de escalamiento
eb config
```

### Variables de Entorno
```bash
# Setear variable única
eb setenv MYSQL_URL="jdbc:mysql://..."

# Setear múltiples variables
eb setenv VAR1="value1" VAR2="value2" VAR3="value3"

# Ver variables
eb printenv

# Eliminar variable
eb setenv VAR1=
```

### Mantenimiento
```bash
# Reiniciar app servers
eb restart

# Rebuild environment
eb rebuild

# Restaurar a una versión anterior
eb deploy --version version-label

# Clonar ambiente
eb clone ecohouse-env -n ecohouse-staging
```

### Limpieza
```bash
# Terminar ambiente (¡cuidado!)
eb terminate ecohouse-env

# Terminar sin confirmación
eb terminate ecohouse-env --force

# Eliminar versiones viejas de la app
eb appversion lifecycle --delete
```

---

## 🗄️ RDS (Base de Datos)

### Crear
```bash
# Crear instancia MySQL
aws rds create-db-instance \
    --db-instance-identifier ecohouse-db \
    --db-instance-class db.t3.micro \
    --engine mysql \
    --master-username admin \
    --master-user-password TU_PASSWORD \
    --allocated-storage 20 \
    --publicly-accessible \
    --db-name ecohouse_bd \
    --region us-east-1
```

### Información
```bash
# Ver información de instancia
aws rds describe-db-instances \
    --db-instance-identifier ecohouse-db

# Ver endpoint
aws rds describe-db-instances \
    --db-instance-identifier ecohouse-db \
    --query 'DBInstances[0].Endpoint.Address' \
    --output text

# Listar todas las instancias
aws rds describe-db-instances
```

### Snapshots
```bash
# Crear snapshot manual
aws rds create-db-snapshot \
    --db-instance-identifier ecohouse-db \
    --db-snapshot-identifier ecohouse-snapshot-$(date +%Y%m%d)

# Listar snapshots
aws rds describe-db-snapshots \
    --db-instance-identifier ecohouse-db

# Restaurar desde snapshot
aws rds restore-db-instance-from-db-snapshot \
    --db-instance-identifier ecohouse-db-restored \
    --db-snapshot-identifier ecohouse-snapshot-20240126
```

### Modificar
```bash
# Cambiar tipo de instancia
aws rds modify-db-instance \
    --db-instance-identifier ecohouse-db \
    --db-instance-class db.t3.small \
    --apply-immediately

# Cambiar contraseña
aws rds modify-db-instance \
    --db-instance-identifier ecohouse-db \
    --master-user-password NuevaPassword123 \
    --apply-immediately
```

### Eliminar
```bash
# Eliminar sin snapshot final
aws rds delete-db-instance \
    --db-instance-identifier ecohouse-db \
    --skip-final-snapshot

# Eliminar con snapshot final
aws rds delete-db-instance \
    --db-instance-identifier ecohouse-db \
    --final-db-snapshot-identifier ecohouse-final-snapshot
```

---

## 🔒 Security Groups

### EC2 Security Groups
```bash
# Listar security groups
aws ec2 describe-security-groups

# Agregar regla (permitir puerto 9000)
aws ec2 authorize-security-group-ingress \
    --group-id sg-xxxxx \
    --protocol tcp \
    --port 9000 \
    --cidr 0.0.0.0/0

# Remover regla
aws ec2 revoke-security-group-ingress \
    --group-id sg-xxxxx \
    --protocol tcp \
    --port 9000 \
    --cidr 0.0.0.0/0
```

---

## 📊 CloudWatch

### Logs
```bash
# Ver log groups
aws logs describe-log-groups

# Ver logs de un grupo
aws logs tail /aws/elasticbeanstalk/ecohouse-env/var/log/eb-engine.log --follow

# Buscar en logs
aws logs filter-log-events \
    --log-group-name /aws/elasticbeanstalk/ecohouse-env/var/log/eb-engine.log \
    --filter-pattern "ERROR"
```

### Métricas
```bash
# Ver métricas de EC2
aws cloudwatch get-metric-statistics \
    --namespace AWS/EC2 \
    --metric-name CPUUtilization \
    --dimensions Name=InstanceId,Value=i-xxxxx \
    --start-time 2024-01-26T00:00:00Z \
    --end-time 2024-01-26T23:59:59Z \
    --period 3600 \
    --statistics Average
```

---

## 🐳 Docker (Opcional)

### Build
```bash
# Build imagen
docker build -t ecohouse-backend .

# Build con tag
docker build -t ecohouse-backend:v1.0.0 .
```

### Run Local
```bash
# Ejecutar localmente
docker run -p 9000:9000 \
    -e MYSQL_URL="jdbc:mysql://host.docker.internal:3306/ecohouse_bd" \
    -e MYSQL_USER="root" \
    -e MYSQL_PASS="root" \
    ecohouse-backend

# Ejecutar en background
docker run -d -p 9000:9000 \
    --name ecohouse \
    ecohouse-backend

# Ver logs
docker logs -f ecohouse

# Detener
docker stop ecohouse
```

### ECR (Elastic Container Registry)
```bash
# Crear repositorio
aws ecr create-repository --repository-name ecohouse-backend

# Login a ECR
aws ecr get-login-password --region us-east-1 | \
    docker login --username AWS --password-stdin \
    ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com

# Tag imagen
docker tag ecohouse-backend:latest \
    ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/ecohouse-backend:latest

# Push a ECR
docker push ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/ecohouse-backend:latest
```

---

## 🔧 Maven

### Compilación
```bash
# Compilar sin tests
mvn clean package -DskipTests

# Compilar con tests
mvn clean package

# Solo tests
mvn test

# Limpiar
mvn clean
```

### Spring Boot
```bash
# Ejecutar localmente
mvn spring-boot:run

# Ejecutar con perfil
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Ejecutar JAR
java -jar target/EcoHouse-0.0.1-SNAPSHOT.jar

# Ejecutar con variables
java -jar target/EcoHouse-0.0.1-SNAPSHOT.jar \
    --spring.profiles.active=prod \
    --server.port=9000
```

---

## 📦 S3 (Storage)

### Buckets
```bash
# Crear bucket
aws s3 mb s3://ecohouse-assets

# Listar buckets
aws s3 ls

# Subir archivo
aws s3 cp archivo.jar s3://ecohouse-assets/

# Descargar archivo
aws s3 cp s3://ecohouse-assets/archivo.jar .

# Sincronizar carpeta
aws s3 sync ./build s3://ecohouse-assets/
```

---

## 🌐 Route 53 (DNS)

### Dominios
```bash
# Listar hosted zones
aws route53 list-hosted-zones

# Crear registro DNS
aws route53 change-resource-record-sets \
    --hosted-zone-id Z123456 \
    --change-batch file://dns-change.json
```

---

## 💰 Billing

### Costos
```bash
# Ver costos del mes actual
aws ce get-cost-and-usage \
    --time-period Start=2024-01-01,End=2024-01-31 \
    --granularity MONTHLY \
    --metrics UnblendedCost

# Ver forecast
aws ce get-cost-forecast \
    --time-period Start=2024-01-26,End=2024-02-26 \
    --metric UNBLENDED_COST \
    --granularity MONTHLY
```

---

## 🚨 Troubleshooting

### Health Checks
```bash
# Probar health check
curl http://tu-app.elasticbeanstalk.com/actuator/health

# Con headers
curl -v http://tu-app.elasticbeanstalk.com/actuator/health
```

### Conectar a MySQL
```bash
# Desde local
mysql -h ecohouse-db.xxxxx.rds.amazonaws.com -u admin -p

# Desde EC2
eb ssh
mysql -h ecohouse-db.xxxxx.rds.amazonaws.com -u admin -p
```

### Ver procesos Java
```bash
# SSH a instancia
eb ssh

# Ver procesos Java
ps aux | grep java

# Ver logs de aplicación
sudo tail -f /var/log/web.stdout.log
sudo tail -f /var/log/eb-engine.log
```

---

## 📋 Aliases Útiles (Agregar a .bashrc o .zshrc)

```bash
# Elastic Beanstalk
alias ebd='eb deploy'
alias ebs='eb status'
alias ebl='eb logs --stream'
alias ebo='eb open'
alias ebr='eb restart'

# Maven
alias mcp='mvn clean package -DskipTests'
alias msr='mvn spring-boot:run'

# Docker
alias dps='docker ps'
alias dlogs='docker logs -f'

# AWS
alias awsl='aws s3 ls'
alias awsprofile='aws configure list'
```

---

## 🎯 Scripts One-Liners Útiles

```bash
# Deploy completo en un comando
mvn clean package -DskipTests && eb deploy && eb open

# Ver logs en tiempo real mientras deployas
eb deploy & sleep 30 && eb logs --stream

# Backup de RDS
aws rds create-db-snapshot \
    --db-instance-identifier ecohouse-db \
    --db-snapshot-identifier backup-$(date +%Y%m%d-%H%M%S)

# Ver todas las URLs de EB
aws elasticbeanstalk describe-environments \
    --query 'Environments[*].[EnvironmentName,CNAME]' \
    --output table

# Limpiar logs locales de Docker
docker system prune -a --volumes
```

---

## 📞 Links Rápidos

- **AWS Console**: https://console.aws.amazon.com
- **EB Console**: https://console.aws.amazon.com/elasticbeanstalk
- **RDS Console**: https://console.aws.amazon.com/rds
- **CloudWatch**: https://console.aws.amazon.com/cloudwatch
- **Billing**: https://console.aws.amazon.com/billing

---

**💡 Tip**: Guarda este archivo como referencia rápida. Los comandos más usados son:
- `eb deploy` - Desplegar
- `eb logs --stream` - Ver logs
- `eb status` - Ver estado
- `mvn clean package -DskipTests` - Compilar

