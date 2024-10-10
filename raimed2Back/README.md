# Raimed2 Backend

## Prerequisites

- **Java 21**: You need to have Java 21 installed on your machine. To verify the installed version, run the following command in your terminal:
  ```bash
  java -version
  ```
  If you don't have Java 21, you can download it here: [OpenJDK 21](https://jdk.java.net/21/).

## How to launch the project in dev mode

### Step 1: Install dependencies

The project uses Maven for dependency management. Use the included Maven Wrapper (`mvnw`) to install all required dependencies. Run the following command from the project's root directory:
```bash
./mvnw clean install
```

This will download all the dependencies and compile the project.

### Step 2: Run the project in development mode

To run the project using the **dev** profile (which will use configurations from `application-dev.properties`), execute the following command:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

This will start the server on port 8080 (by default). You can access the application at [http://localhost:8080](http://localhost:8080).

## Troubleshooting

If you encounter issues while running the project, you can enable debug mode to get more detailed error messages:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -X
```

This will provide additional information to help diagnose any problems.
