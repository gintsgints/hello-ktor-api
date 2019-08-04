package eu.techwares.demo.entity.blog

import org.jetbrains.squash.definition.*

object BlogModel : TableDefinition() {
    val id = uuid("id")
    val message = varchar("message", 1000)
//    val userId = reference(UserModel.id, "user_id").nullable()
}
