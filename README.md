# Startup project for API using ktor

Project is work in progress for API made with ktor framework.

Components used:

- ktor & project is created in kotlin language
- ktor - as basis
- squash - for database connections
- kodein - dependency injection

# Getting Started

- start database with docker:

```
docker-compuse up -d db
```

- then compile and start API

```
gradlew run
```

- check working api

```
curl -X POST "http://localhost:8080/user" -H "accept: application/json"
curl -X GET "http://localhost:8080/user" -H "accept: application/json"
```
