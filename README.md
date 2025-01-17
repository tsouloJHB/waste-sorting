# Waste Sorting Mobile Application - Backend Service

## Project Overview
The **Waste Sorting Mobile Application** backend is a Spring Boot application developed as part of the Graduate Software Developer Assessment for Enviro365. The backend exposes REST APIs that provide functionalities such as waste category lookup, disposal guidelines retrieval, and recycling tips. This project is aimed at promoting sustainable waste management practices by facilitating data exchange between the frontend mobile application and the backend server.

---

## Features
- RESTful APIs for managing:
  - Waste categories
  - Disposal guidelines
  - Recycling tips
- Input validation using Spring Boot validation annotations.
- JSON-formatted responses.
- In-memory database (H2) for data persistence.

---

## Prerequisites
Ensure you have the following installed on your system:
- Java 21 or higher
- Maven 3.8+
- Docker (if you want to run the application in a containerized environment)

---

## How to Run the Application

### 1. Running the Application Locally

#### Clone the Repository
```bash
# Replace <repository-url> with your GitHub repository link
git clone <repository-url>
cd waste-sorting
```

#### Build the Application
```bash
mvn clean install
```

#### Run the Application
```bash
mvn spring-boot:run
```

The application will start and be accessible at `http://localhost:8080`.

#### Test the APIs
Use tools like **Postman** or **cURL** to test the REST endpoints.

#### Example Endpoints:
- Retrieve all waste categories:
  ```http
  GET http://localhost:8080/api/waste-categories
  ```
- Add a new waste category:
  ```http
  POST http://localhost:8080/api/waste-categories
  Content-Type: application/json

  {
    "name": "Plastic",
    "description": "Recyclable plastic materials."
  }
  ```

### 2. Running the Application Using Docker

#### Build the Docker Image
Make sure your terminal is in the project’s root directory where the `Dockerfile` is located.

```bash
docker build -t waste-sorting-app .
```

#### Run the Docker Container
```bash
docker run -p 8080:8080 waste-sorting-app
```

The application will now be accessible at `http://localhost:8080`.

##Access Swagger API Documentation
Once the application is running, you can access the Swagger UI for API documentation and testing via the following URL in your web browser:

http://localhost:8080/swagger-ui/index.html

### How to Run Tests
### 1. Running Unit Tests Locally
You can run the tests for the application using Maven with the following command:


```bash
mvn test
```
### 2. Running Specific Tests
To run specific tests, use the following command:

```bash
mvn -Dtest=<TestClassName>#<TestMethodName> test
```
For example, to run a test method testWasteCategoryRetrieval from the WasteCategoryServiceTest class:
```bash
mvn -Dtest=WasteCategoryServiceTest#testWasteCategoryRetrieval test
```


---

## Project Structure
```
- src/
  - main/
    - java/
      - com/enviro/assessment/grad001/thabangsoulo/
        - controllers/    # REST controllers
        - services/       # Service layer
        - repositories/   # Data access layer
        - models/         # Domain models
    - resources/
      - application.properties  # Configuration file
  - test/
    - java/
      - com/enviro/assessment/grad001/thabangsoulo/
        - ... (unit tests)
- pom.xml
- Dockerfile
```

---

## Configuration

### H2 In-Memory Database
The application uses H2 for data persistence during development. To access the H2 console:
1. Start the application.
2. Navigate to `http://localhost:8080/h2-console`.
3. Enter the following credentials:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **Username**: `sa`
   - **Password**: `password`

You can update these properties in `src/main/resources/application.properties`.

---

## Submission Instructions
1. Push your code to a GitHub repository.
2. Submit the repository link on [eTalente](https://www.etalente.co.za).

---

## Additional Notes
- Ensure your code includes comments explaining key design decisions.
- Follow best practices for Object-Oriented Design.
- Be prepared to discuss your design choices during a subsequent interview.

---

## Contact
For any questions or assistance, please contact the Enviro365 team.

