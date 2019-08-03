package eu.techwares.demo.entity.user

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*

fun Route.userIndex(userService: UserService) {
    route("/user") {
        post("/") {
            val user = call.receive<User>()
            call.respond(HttpStatusCode.OK, userService.insertUser(user))
        }

        get("/") {
            call.respond(HttpStatusCode.OK, userService.findAll())
        }
    }
}
