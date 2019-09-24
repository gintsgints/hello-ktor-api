package eu.techwares.demo

import eu.techwares.demo.entity.blog.BlogDao
import eu.techwares.demo.entity.user.UserDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.gson2.Gson2Plugin
import org.jdbi.v3.json.JsonPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

class Database(url: String, username: String, password: String) {
    val userDao: UserDao
    val blogDao: BlogDao
    val jdbi = Jdbi.create(url, username, password)
        .installPlugin(KotlinSqlObjectPlugin())
        .installPlugin(SqlObjectPlugin())
        .installPlugin(KotlinPlugin())
        .installPlugin(PostgresPlugin())
        .installPlugin(JsonPlugin())
        .installPlugin(Gson2Plugin())

    init {
        userDao = jdbi.onDemand(UserDao::class.java)
        userDao.createTable()
        blogDao = jdbi.onDemand(BlogDao::class.java)
        blogDao.createTable()
    }
}
