package eu.techwares.demo.entity.blog

import eu.techwares.demo.entity.user.User
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.query.from
import org.jetbrains.squash.query.limit
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.fetch
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values

data class Blog(val id: String?, val message: String, val user: User? )

class BlogService(private val db: DatabaseConnection) {
    init {
        db.transaction {
            databaseSchema().create(Blogs)
        }
    }

    fun insertBlog(blog: Blog): Number {
        return db.transaction {
            insertInto(Blogs).values {
                it[message] = blog.message
            }.fetch(Blogs.id).execute()
        }
    }

    fun findAll(): Set<ResultRow> {
        return db.transaction {
            from(Blogs)
                .limit(100)
                .execute()
                .toSet()
        }
    }

}