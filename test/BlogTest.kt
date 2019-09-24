package eu.techwares.demo

import com.google.gson.Gson
import eu.techwares.demo.entity.blog.Blog
import eu.techwares.demo.entity.miniUUID
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.*

@KtorExperimentalAPI
class BlogTest {
    private val gson = Gson()
    private val database = Database(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "postgres_234"
    )

    @BeforeTest
    fun prepareDB() {
        database.jdbi.open().use { handle ->
            handle.execute("delete from blogs where true ")
        }
    }

    @AfterTest
    fun cleanupDB() {
        database.jdbi.open().use { handle ->
            handle.execute("delete from blogs where true ")
        }
    }

    @Test
    fun testSimpleBlogSelect() {
        val myBlog = Blog(miniUUID(), "Blog Message")
        database.blogDao.insert(myBlog)

        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/blog").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
                val responseObj = gson.fromJson(response.content, Array<Blog>::class.java)
                assertNotNull(responseObj)
                assertEquals(1, responseObj.size)
                assertEquals(myBlog.message, responseObj[0].message)
            }
        }
    }

    @Test
    fun testInsertBlogRecord() {
        val myBlog = Blog(null, "Blog Message")

        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/blog") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(gson.toJson(myBlog))
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertNotNull(response.content)
                val responseObj = gson.fromJson(response.content, Blog::class.java)
                assertNotNull(responseObj)
                assertEquals(myBlog.message, responseObj.message)
            }
        }
    }

    @Test
    fun testUpdateBlogRecord() {
        val id = miniUUID()
        val myBlog = Blog(id, "Blog Message")
        database.blogDao.insert(myBlog)
        val updBlog = myBlog.copy(message = "Updated Message")

        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Put, "/api/blog/") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(gson.toJson(updBlog))
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
                val responseObj = gson.fromJson(response.content, Blog::class.java)
                assertNotNull(responseObj)
                assertEquals(updBlog.message, responseObj.message)
            }
        }
    }
}