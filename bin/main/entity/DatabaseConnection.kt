package eu.techwares.demo.entity

import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.dialects.postgres.PgConnection

fun db(): DatabaseConnection = PgConnection.create("localhost:5432/postgres", "postgres", "postgres_234")
