package eu.techwares.demo

import eu.techwares.demo.entity.blog.BlogDao
import eu.techwares.demo.entity.user.UserDao
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.routing
import io.ktor.gson.gson
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.kodein

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        gson {}
    }

    val database = Database(
        environment.config.propertyOrNull("ktor.database.url")?.getString()?: "jdbc:postgresql://localhost:5432/postgres",
        environment.config.propertyOrNull("ktor.database.user")?.getString()?: "postgres",
        environment.config.propertyOrNull("ktor.database.password")?.getString()?: "postgres_234"
    )

    kodein {
        bind<Database>() with singleton { database }
        bind<UserDao>() with singleton { database.userDao }
        bind<BlogDao>() with singleton { database.blogDao }
    }

    routing {
        apiRouter()
    }
}
