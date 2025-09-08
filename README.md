# CityPay Core Monorepo (Groovy Gradle, Java 21)

A multi-project Gradle skeleton for CityPay crypto transaction microservices. The build uses the Groovy DSL and targets Java 21.

## Project Structure
- `libs/shared` – shared Java library.
- `services/balance` – Spring Boot service that depends on `libs/shared`.

## Getting Started
1. Install JDK 21.
2. Build and test the project:
   ```
   ./gradlew build
   ```
3. Run the balance service:
   ```
   ./gradlew :services:balance:bootRun
   ```

## Notes
- Group ID: `io.citypay.core`
- Java target: `21`
- Extend with additional modules under `libs/` and `services/` as needed.

