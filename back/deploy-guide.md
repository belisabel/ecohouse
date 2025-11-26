# 🚀 Guía de Despliegue de EcoHouse Backend en AWS

## Opción 1: AWS Elastic Beanstalk (Recomendado)

### Requisitos previos:
1. Cuenta de AWS activa
2. AWS CLI instalado: https://aws.amazon.com/cli/
3. EB CLI instalado: `pip install awsebcli`

### Paso 1: Configurar AWS CLI
```bash
aws configure
# Ingresa tu AWS Access Key ID
# Ingresa tu AWS Secret Access Key
# Región: us-east-1 (o tu preferida)
# Output format: json
```

### Paso 2: Compilar la aplicación
```bash
cd C:\Users\isabe\Documents\NoCountry_E_commerce\proyecto\EcoHouse\back
mvn clean package -DskipTests
```

### Paso 3: Inicializar Elastic Beanstalk
```bash
eb init -p corretto-21 ecohouse-api --region us-east-1
```

### Paso 4: Crear base de datos MySQL en AWS RDS
1. Ve a AWS Console → RDS
2. Crear base de datos → MySQL
3. Plantilla: Free tier o Producción según necesites
4. Nombre de BD: ecohouse_bd
5. Usuario maestro: admin
6. Contraseña: (guárdala)
7. Conectividad: Acceso público (SÍ para desarrollo)
8. Grupo de seguridad: Permitir puerto 3306

### Paso 5: Configurar variables de entorno
```bash
eb create ecohouse-env

# Una vez creado, configurar variables:
eb setenv MYSQL_URL="jdbc:mysql://TU-RDS-ENDPOINT:3306/ecohouse_bd?useSSL=true&serverTimezone=UTC" \
          MYSQL_USER="admin" \
          MYSQL_PASS="TU_PASSWORD" \
          SPRING_PROFILES_ACTIVE="prod"
```

### Paso 6: Desplegar
```bash
eb deploy
```

### Paso 7: Abrir la aplicación
```bash
eb open
# Para ver Swagger: https://tu-app.elasticbeanstalk.com/swagger-ui/index.html
```

### Comandos útiles:
```bash
eb status          # Ver estado
eb logs            # Ver logs
eb ssh             # Conectar por SSH
eb terminate       # Eliminar ambiente (¡cuidado!)
```

---

## Opción 2: AWS EC2 (Servidor tradicional)

### Paso 1: Crear instancia EC2
1. AWS Console → EC2 → Launch Instance
2. Nombre: ecohouse-backend
3. AMI: Amazon Linux 2023
4. Tipo de instancia: t2.micro (free tier) o t2.small
5. Key pair: Crear/usar una key pair
6. Grupo de seguridad: Permitir puertos 22 (SSH), 9000 (app), 3306 (MySQL si usas RDS)

### Paso 2: Conectar por SSH
```bash
ssh -i "tu-keypair.pem" ec2-user@tu-ip-publica
```

### Paso 3: Instalar Java 21 en EC2
```bash
sudo yum update -y
sudo yum install java-21-amazon-corretto -y
java -version
```

### Paso 4: Instalar MySQL (o usar RDS)
```bash
sudo yum install mysql -y
# O conectar a RDS existente
```

### Paso 5: Subir tu aplicación
Opción A - Desde tu PC:
```bash
scp -i "tu-keypair.pem" target/EcoHouse-0.0.1-SNAPSHOT.jar ec2-user@tu-ip-publica:/home/ec2-user/
```

Opción B - Clonar desde Git:
```bash
# En EC2:
sudo yum install git -y
git clone https://github.com/tu-usuario/ecohouse.git
cd ecohouse/back
# Instalar Maven
sudo yum install maven -y
mvn clean package -DskipTests
```

### Paso 6: Crear servicio systemd
```bash
sudo nano /etc/systemd/system/ecohouse.service
```

Contenido:
```ini
[Unit]
Description=EcoHouse Backend
After=network.target

[Service]
User=ec2-user
WorkingDirectory=/home/ec2-user
ExecStart=/usr/bin/java -jar /home/ec2-user/EcoHouse-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

Environment="MYSQL_URL=jdbc:mysql://tu-rds-endpoint:3306/ecohouse_bd"
Environment="MYSQL_USER=admin"
Environment="MYSQL_PASS=tu-password"
Environment="SPRING_PROFILES_ACTIVE=prod"

[Install]
WantedBy=multi-user.target
```

### Paso 7: Iniciar servicio
```bash
sudo systemctl daemon-reload
sudo systemctl enable ecohouse
sudo systemctl start ecohouse
sudo systemctl status ecohouse
```

### Paso 8: Ver logs
```bash
sudo journalctl -u ecohouse -f
```

---

## Opción 3: AWS ECS con Docker

### Paso 1: Construir imagen Docker
```bash
cd C:\Users\isabe\Documents\NoCountry_E_commerce\proyecto\EcoHouse\back
docker build -t ecohouse-backend .
```

### Paso 2: Probar localmente
```bash
docker run -p 9000:9000 \
  -e MYSQL_URL="jdbc:mysql://host.docker.internal:3306/ecohouse_bd" \
  -e MYSQL_USER="root" \
  -e MYSQL_PASS="root" \
  ecohouse-backend
```

### Paso 3: Subir a Amazon ECR
```bash
# Crear repositorio
aws ecr create-repository --repository-name ecohouse-backend --region us-east-1

# Login
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin TU_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com

# Tag
docker tag ecohouse-backend:latest TU_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/ecohouse-backend:latest

# Push
docker push TU_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/ecohouse-backend:latest
```

### Paso 4: Crear cluster ECS
1. AWS Console → ECS → Create Cluster
2. Cluster name: ecohouse-cluster
3. Infrastructure: AWS Fargate
4. Create

### Paso 5: Crear Task Definition
1. Create new Task Definition
2. Family: ecohouse-task
3. Container:
   - Name: ecohouse-backend
   - Image URI: (tu ECR URI)
   - Port: 9000
   - Environment variables: MYSQL_URL, MYSQL_USER, MYSQL_PASS

### Paso 6: Crear servicio
1. Create Service
2. Launch type: Fargate
3. Task Definition: ecohouse-task
4. Service name: ecohouse-service
5. Number of tasks: 1 (o más)
6. Load balancer: Optional pero recomendado

---

## 🔒 Configuración de Base de Datos (AWS RDS)

### Crear RDS MySQL:
1. AWS Console → RDS → Create database
2. Engine: MySQL 8.0
3. Templates: Free tier (para pruebas)
4. DB instance identifier: ecohouse-db
5. Master username: admin
6. Master password: (crear una segura)
7. DB instance class: db.t3.micro
8. Storage: 20 GB
9. Connectivity:
   - Public access: Yes (para desarrollo)
   - VPC security group: Crear nuevo
   - Port: 3306
10. Additional configuration:
    - Initial database name: ecohouse_bd

### Configurar Security Group:
Permitir conexión desde:
- Tu IP local (para desarrollo)
- Security group de EC2/ECS/EB (para producción)

---

## 📊 Monitoreo y Logs

### CloudWatch Logs:
```bash
# Elastic Beanstalk configura automáticamente logs
# EC2: Instalar CloudWatch agent
# ECS: Logs automáticos en CloudWatch
```

### Health Checks:
- Endpoint: `/actuator/health`
- Puerto: 9000
- Protocolo: HTTP

---

## 💰 Estimación de Costos (Free Tier)

- **Elastic Beanstalk**: Gratis (pagas por los recursos subyacentes)
- **EC2 t2.micro**: Gratis primer año (750 horas/mes)
- **RDS db.t3.micro**: Gratis primer año (750 horas/mes)
- **S3/CloudWatch**: Límites gratuitos generosos

---

## 🔧 Troubleshooting

### La app no inicia:
```bash
# Ver logs
eb logs              # Elastic Beanstalk
sudo journalctl -u ecohouse -f  # EC2
aws logs tail /aws/elasticbeanstalk/ecohouse-env/var/log/eb-engine.log --follow  # EB detallado
```

### No se conecta a MySQL:
1. Verificar security group permite puerto 3306
2. Verificar endpoint de RDS es correcto
3. Verificar usuario/contraseña
4. Probar conexión: `mysql -h TU-RDS-ENDPOINT -u admin -p`

### Error 502/504:
1. Verificar la app está escuchando en el puerto correcto (5000 para EB, 9000 para otros)
2. Verificar health check pasa
3. Aumentar timeout en load balancer

---

## 📝 Checklist Pre-Despliegue

- [ ] Compilación exitosa: `mvn clean package`
- [ ] Base de datos creada en RDS
- [ ] Variables de entorno configuradas
- [ ] Security groups configurados
- [ ] Swagger accesible: `/swagger-ui/index.html`
- [ ] Health check funciona: `/actuator/health`
- [ ] CORS configurado para dominio de producción

---

## 🌐 Actualizar Swagger con URL de producción

Después del despliegue, actualiza `SwaggerConfig.java`:

```java
new Server()
    .url("https://tu-app.elasticbeanstalk.com")
    .description("Servidor de producción AWS")
```

---

## 📞 Soporte

Si tienes problemas:
1. Revisa los logs
2. Verifica security groups
3. Prueba health checks
4. Consulta documentación de AWS

¡Éxito con el despliegue! 🚀

