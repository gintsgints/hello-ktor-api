package eu.techwares.demo.entity.user

import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.query.*
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values

class UserService(private val db: DatabaseConnection) {
    init {
        db.transaction {
            databaseSchema().create(UserModel)
        }
    }

    fun insertUser() {
        return db.transaction {
            insertInto(UserModel).values {
                it[id] = "eugene"
                it[email] = "Eugene"
                it[displayName] = "displayName"
                it[passwordHash] = "passwordHash"
            }.execute()        }
    }

    fun findAll(): List<ResultRow> {
        return db.transaction {
            from(UserModel)
                .limit(100)
                .execute()
                .toList()
        }
    }
}
