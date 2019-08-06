package eu.techwares.demo.entity.blog

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

data class Blog(val id: String?, val message: String)

class BlogService() {
    init {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(BlogModel)
        }
    }

    fun insertBlog(blog: Blog) {
        return transaction {
            val newblog = BlogModel.insert {
                it[message] = blog.message
            } get BlogModel.id
            println(newblog)
        }
    }

    fun findAll(): List<ResultRow> {
        val blogList = transaction {
            BlogModel.selectAll().toList()
        }
        return blogList
    }

}