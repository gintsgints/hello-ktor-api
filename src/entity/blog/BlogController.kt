package eu.techwares.demo.entity.blog

import eu.techwares.demo.entity.ApiResponse
import eu.techwares.demo.entity.miniUUID
import eu.techwares.demo.entity.user.User
import eu.techwares.demo.entity.user.UserDao
import eu.techwares.demo.entity.user.UserRegister
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

fun Route.blogController() {
    val blogDao by kodein().instance<BlogDao>()

    route("/blog") {
        get("/") {
            val blogs = blogDao.findAll()
            call.respond(HttpStatusCode.OK, blogs)
        }
        get("/{id}") {
            val id = call.parameters["id"]!!
            val blog = blogDao.findById(id)
            if (blog == null) {
                call.respond(HttpStatusCode.NotFound, {})
            } else {
                call.respond(HttpStatusCode.OK, blog)
            }
        }
        post("/") {
            val blog = call.receive<Blog>().copy(id = miniUUID())
            blogDao.insert(blog)
            call.respond(HttpStatusCode.Created, blog)
        }
        put("/") {
            val gym = call.receive<Blog>()
            blogDao.update(gym)
            call.respond(HttpStatusCode.OK, gym)
        }
        delete("/{id}") {
            val id = call.parameters["id"]!!
            blogDao.delete(id)
            call.respond(HttpStatusCode.OK, ApiResponse("Blog deleted"))
        }
    }
}
