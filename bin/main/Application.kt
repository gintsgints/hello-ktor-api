package eu.techwares.demo

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.routing
import com.fasterxml.jackson.databind.*
import eu.techwares.demo.entity.db
import eu.techwares.demo.entity.user.UserService
import io.ktor.jackson.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import eu.techwares.demo.entity.user.userIndex
import org.jetbrains.squash.connection.DatabaseConnection

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val kodein = Kodein {
        bind <DatabaseConnection>() with singleton  { db() }
        bind<UserService>() with singleton { UserService(instance()) }
    }
    val userService by kodein.instance<UserService>()

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        userIndex(userService)
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

