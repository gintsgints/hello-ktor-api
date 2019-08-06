package eu.techwares.demo.entity.user

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class User(val id: String?, val email: String, val displayName: String, val password: String )

class UserService() {
    init {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserModel)
        }
    }

    fun insertUser(user: User) {
        return transaction {
            UserModel.insert {
                it[email] = user.email
                it[displayName] = user.displayName
                it[passwordHash] = """Encrypted password ${user.password}"""
            }
        }
    }

    fun findAll() {
        return transaction {
            UserModel.selectAll()
        }
    }
}
