package eu.techwares.demo

import eu.techwares.demo.entity.blog.BlogModel
import io.ktor.http.*
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BlogTest {
    @BeforeTest
    fun clearDB() {
        Database.connect("jdbc:postgresql://localhost:5432/postgres", user="postgres", password = "postgres_234", driver = "org.postgresql.Driver")
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(BlogModel)
            BlogModel.deleteAll()
        }
    }

    @Test
    fun testEmptyBlogList() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/blog").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[ ]", response.content)
            }
        }
    }

    @Test
    fun testInsertBlog() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/blog") {
                addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{ "message" : "my first blog entry" }""")
            }.response.let { response ->
                assertEquals(HttpStatusCode.Created, response.status())
                assertNotNull(response.content)
                assertEquals(listOf("""id=1, message=Hello, World!"""), response.content!!.lines())
                val contentTypeText = assertNotNull(response.headers[HttpHeaders.ContentType])
                assertEquals(ContentType.Text.Plain.withCharset(Charsets.UTF_8), ContentType.parse(contentTypeText))
            }
        }
    }
}