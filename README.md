# CityPay Core Monorepo (Groovy Gradle, Java 21)

Multi-project Gradle monorepo skeleton for crypto transactions core microservices using Groovy DSL Gradle files and Java 21.

Structure:
- libs/shared  : shared Java library used by services
- services/balance : Spring Boot service that depends on libs/shared

Group ID: io.citypay.core
Java target: 21

Quick run (from repository root):
1. Install a JDK 21
2. Run the balance service:
   ./gradlew :services:balance:bootRun

Expand with more modules, CI, and Docker as needed.
