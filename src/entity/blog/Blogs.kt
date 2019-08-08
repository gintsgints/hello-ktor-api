package eu.techwares.demo.entity.blog

import org.jetbrains.squash.definition.*

object Blogs : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val message = varchar("message", 1000)
//    val userId = reference(UserModel.id, "user_id").nullable()
}
