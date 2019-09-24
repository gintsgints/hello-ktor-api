package eu.techwares.demo.entity.blog

import org.jdbi.v3.sqlobject.SingleValue
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface BlogDao {
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS blogs
          (
             id             VARCHAR(50) PRIMARY KEY NOT NULL,
             message        VARCHAR(100) NOT NULL
          )
    """)
    fun createTable()

    @SqlUpdate(
        """
        INSERT INTO blogs(id, message)
        VALUES (:blog.id, :blog.message)
    """
    )
    fun insert(blog: Blog)

    @SqlQuery("SELECT * FROM blogs")
    fun findAll(): List<Blog>

    @SqlQuery("SELECT * FROM blogs WHERE blogs.id = :blogId")
    @SingleValue
    fun findById(blogId: String): Blog?

    @SqlUpdate("""
        UPDATE blogs
        SET message    = :blog.message
        WHERE blogs.id = :blog.id
    """)
    fun update(blog: Blog)

    @SqlUpdate("DELETE FROM blogs WHERE id = :blogId")
    fun delete(blogId: String)

    @SqlUpdate("DELETE FROM blogs WHERE true")
    fun deleteAll()
}