package eu.techwares.demo.entity.user

import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.query.*
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import java.util.UUID

data class User(val id: String?, val email: String, val displayName: String, val password: String )

class UserService(private val db: DatabaseConnection) {
    init {
        db.transaction {
            databaseSchema().create(UserModel)
        }
    }

    fun insertUser(user: User) {
        return db.transaction {
            insertInto(UserModel).values {
                it[id] = UUID.randomUUID()
                it[email] = user.email
                it[displayName] = user.displayName
                it[passwordHash] = """Encrypted password ${user.password}"""
            }.execute()        }
    }

    fun findAll(): Set<ResultRow> {
        return db.transaction {
            from(UserModel)
                .limit(100)
                .execute()
                .toSet()
        }
    }
}
