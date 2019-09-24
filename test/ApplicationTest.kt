package eu.techwares.demo

import com.google.gson.Gson
import eu.techwares.demo.entity.ApiResponse
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import io.ktor.http.*
import kotlin.test.*

@KtorExperimentalAPI
class ApplicationTest {
    private val gson = Gson()

    @Test
    fun testFindOne() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/").apply {

                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
                val responseObj = gson.fromJson(response.content, ApiResponse::class.java)
                assertNotNull(responseObj)
                assertNotNull(responseObj.message)
                assertEquals("Ktor REST api", responseObj.message)
            }
        }
    }
}
