package eu.techwares.demo.entity.user

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.userIndex(userService: UserService) {
    route("/user") {
        post("/") {
            call.respond(HttpStatusCode.OK, userService.insertUser())
        }

        get("/") {
            call.respond(HttpStatusCode.OK, userService.findAll())
        }
    }
}
