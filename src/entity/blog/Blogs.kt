package eu.techwares.demo.entity.blog

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Blogs: Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val message = varchar("message", 1000)
}
