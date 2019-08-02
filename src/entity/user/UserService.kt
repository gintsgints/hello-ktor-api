package eu.techwares.demo.entity.user

import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.query.*
import org.jetbrains.squash.schema.create

class UserService(private val db: DatabaseConnection) {
    init {
        db.transaction {
            databaseSchema().create(UserModel)
        }
    }

    fun findAll() {
        db.transaction {
            from(UserModel)
                .limit(100)
                .execute()
                .toList()
        }
    }
}
