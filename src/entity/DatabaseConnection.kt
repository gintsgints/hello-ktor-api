package eu.techwares.demo.entity

import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.dialects.h2.H2Connection

fun db(): DatabaseConnection = H2Connection.createMemoryConnection()
