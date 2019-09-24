package eu.techwares.demo.entity.user

import eu.techwares.demo.entity.miniUUID
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

fun Route.userController() {
    val userDao by kodein().instance<UserDao>()

    route("/user") {
        get("/{id}") {
            val id = call.parameters["id"]!!
            call.respond(HttpStatusCode.OK, userDao.selectForUser(id))
        }
        post("/") {
            val (id, email, displayName, password) = call.receive<UserRegister>().copy(id = miniUUID())
            val user = User(id, email, displayName, passwordHash = """$password encrypted""")
            userDao.insert(user)
            call.respond(HttpStatusCode.Created, user)
        }
    }
}
