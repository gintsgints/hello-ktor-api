# Startup project for API using ktor

Project is work in progress for API made with ktor framework.

Components used:

- ktor & project is created in kotlin language
- ktor - as basis
- exposed - for database connections
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
curl -X POST "http://localhost:8080/api/blog" -H "accept: application/json" -H "Content-Type: application/json" -d '{"message": "First blog message"}'
curl -X GET "http://localhost:8080/api/blog/{id_you_got_form_previous}" -H "accept: application/json"
```

# Auto reload.

Autoreload is configured to work within two envents

- watch for source changes and recompile - `gradlew -t build`
- in ther console run server with hotreload updated classes `gradlew run`
