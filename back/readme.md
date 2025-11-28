# EcoHouse Backend API

RESTful API for EcoHouse environmental impact tracking application.

## Features

- Environmental impact reports generation
- CO2 tracking and carbon footprint calculation
- Eco points system
- Sustainable products tracking
- User management and authentication

## Tech Stack

- Java 21
- Spring Boot 3.5.7
- MySQL 8.0
- AWS Elastic Beanstalk
- GitHub Actions CI/CD

## Deployment

The application is automatically deployed to AWS Elastic Beanstalk on every push to the `main` branch.

- **Production URL**: http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com
- **Swagger UI**: http://ecohouse-env.eba-vay8q3u6.us-east-1.elasticbeanstalk.com/swagger-ui/index.html

## CI/CD Pipeline

GitHub Actions workflow handles:
- Automated testing
- Maven build
- Deployment to AWS
- Health check verification

## Local Development

```bash
# Clone repository
git clone https://github.com/belisabel/ecohouse.git

# Navigate to backend
cd ecohouse/back

# Run with Maven
mvn spring-boot:run
```

## Testing

```bash
# Run tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## API Documentation

Full API documentation available at `/swagger-ui/index.html` when running.

