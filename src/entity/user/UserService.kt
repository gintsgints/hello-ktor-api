package eu.techwares.demo.entity.user

import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.schema.create
import java.io.*

class UserService(val db: DatabaseConnection = H2Connection.createMemoryConnection()) {
    constructor(dir: File) : this(H2Connection.create("jdbc:h2:file:${dir.canonicalFile.absolutePath}"))

    init {
        db.transaction {
            databaseSchema().create(UserModel)
        }
    }

}