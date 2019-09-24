package eu.techwares.demo

import eu.techwares.demo.entity.ApiResponse
import eu.techwares.demo.entity.blog.blogController
import eu.techwares.demo.entity.user.userController
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.routing.get

fun Routing.apiRouter() {

    route("/api") {
        get("/") {
            call.respond(HttpStatusCode.OK, ApiResponse("Ktor REST api"))
        }
        userController()
        blogController()
    }
}
