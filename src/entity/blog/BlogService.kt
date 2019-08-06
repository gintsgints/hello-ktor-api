package eu.techwares.demo.entity.blog

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

data class Blog(val id: Number?, val message: String)

class BlogService() {
    init {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Blogs)
        }
    }

    fun insertBlog(blog: Blog): Number {
        return transaction {
            Blogs.insert {
                it[message] = blog.message
            }[Blogs.id]
        }
    }

    fun findAll(): List<Blog> {
        return transaction {
            Blogs.selectAll().map {
                Blog(it[Blogs.id], it[Blogs.message])
            }
        }
    }

}