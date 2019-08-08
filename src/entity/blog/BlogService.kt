package eu.techwares.demo.entity.blog

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import service.dbQuery

data class Blog(val id: Number?, val message: String)

class BlogService() {
    init {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Blogs)
        }
    }

    suspend fun getBlog(id: Int): Blog? = dbQuery {
        Blogs.select {
            (Blogs.id eq id)
        }.mapNotNull { toBlog(it) }
            .singleOrNull()
    }

    suspend fun insertBlog(blog: Blog): Blog {
        var key = 0
        dbQuery {
            key = (Blogs.insert {
                it[message] = blog.message
            } get Blogs.id)
        }
        return getBlog(key)!!
    }

    fun findAll(): List<Blog> {
        return transaction {
            Blogs.selectAll().map {
                toBlog(it)
            }
        }
    }

    private fun toBlog(row: ResultRow): Blog =
        Blog(
            id = row[Blogs.id],
            message = row[Blogs.message]
        )
}