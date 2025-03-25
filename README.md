---

# SpringBoot-CRUD-App  

This project is a CRUD operation-based backend application built using **Spring Boot**. It leverages **H2 database**, **JPA**, and **Hibernate** for data persistence. The project follows best practices in Spring Boot development and demonstrates key concepts such as RESTful APIs, entity mapping, and database integration. The application is containerized using **Docker** for easy deployment.  

## Tech Stack  

- **Backend Framework:** Spring Boot  
- **Database:** H2 (in-memory)  
- **ORM:** Hibernate & JPA  
- **Build Tool:** Maven  
- **Containerization & Deployment:** Docker  

## Installation and Setup  

Follow these steps to set up and run the project locally:  

### 1. Clone the repository  

Open a terminal and run:  

```bash
git clone <repository-url>
cd SpringBoot-CRUD-App
```

### 2. Configure the database  

Navigate to `src/main/resources/application.properties` and add the required H2 database credentials:  

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 3. Build and generate the JAR file  

Run the following Maven command to build the project and generate the target JAR file:  

```bash
mvn clean package
```

The JAR file will be generated in the `target/` directory.  

### 4. Install Docker (if not already installed)  

To verify Docker installation, run:  

```bash
docker --version
```

### 5. Create a Docker image  

Ensure you have a `Dockerfile` in the project directory. Then, build the Docker image using:  

```bash
docker build -t springboot-crud-app .
```

### 6. Run the Docker container  

Start the container on port **8080** using:  

```bash
docker run --rm -p 8080:8080 springboot-crud-app
```

### 7. Access the application  

Once the container is running, open your browser and go to:  

```
http://localhost:8080
```

If H2 Console is enabled, access the database at:  

```
http://localhost:8080/h2-console
```

Use the JDBC URL `jdbc:h2:mem:testdb` and the credentials defined in `application.properties` to log in.  

Your Spring Boot application is now up and running! 🚀  

---
