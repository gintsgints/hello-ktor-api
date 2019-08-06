package eu.techwares.demo.entity.user

import org.jetbrains.exposed.sql.Table

object UserModel: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val email = varchar("email", 128).uniqueIndex()
    val displayName = varchar("display_name", 256)
    val passwordHash = varchar("password_hash", 64)
}
