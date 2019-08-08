package eu.techwares.demo.entity

import io.ktor.application.ApplicationEnvironment
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.dialects.postgres.PgConnection

fun db(environment: ApplicationEnvironment): DatabaseConnection {
    return PgConnection.create(
        environment.config.propertyOrNull("ktor.database.url")?.getString()?: "localhost:5432/postgres",
        environment.config.propertyOrNull("ktor.database.user")?.getString()?: "postgres",
        environment.config.propertyOrNull("ktor.database.password")?.getString()?: "postgres_234"
    )
}
