package eu.techwares.demo.entity.blog

import org.jetbrains.exposed.sql.Table

object BlogModel: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val message = varchar("message", 1000)
}
