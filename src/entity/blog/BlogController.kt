package eu.techwares.demo.entity.blog

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.blogController(blogService: BlogService) {
    route("/blog") {
        post("/") {
            val blog = call.receive<Blog>()
            val retval = blogService.insertBlog(blog)
            call.respond(HttpStatusCode.Created, retval)
        }

        get("/") {
            call.respond(HttpStatusCode.OK, blogService.findAll())
        }
    }
}
