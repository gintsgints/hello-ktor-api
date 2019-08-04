package eu.techwares.demo.entity.blog

import eu.techwares.demo.entity.user.User
import eu.techwares.demo.entity.user.UserModel
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.query.from
import org.jetbrains.squash.query.limit
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import java.util.*

data class Blog(val id: String?, val message: String, val user: User )

class BlogService(private val db: DatabaseConnection) {
    init {
        db.transaction {
            databaseSchema().create(BlogModel)
        }
    }

    fun insertUser(blog: Blog) {
        return db.transaction {
            insertInto(BlogModel).values {
                it[id] = UUID.randomUUID()
                it[message] = blog.message
            }.execute()        }
    }

    fun findAll(): Set<ResultRow> {
        return db.transaction {
            from(BlogModel)
                .limit(100)
                .execute()
                .toSet()
        }
    }

}