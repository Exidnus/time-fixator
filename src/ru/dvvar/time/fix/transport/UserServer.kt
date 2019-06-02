package ru.dvvar.time.fix.transport

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import ru.dvvar.time.fix.port.rest.NewUser
import ru.dvvar.time.fix.port.rest.UserPort

class UserServer(private val userPort: UserPort) : AbstractServer() {
    override fun routingConfiguration(): Routing.() -> Unit = {
        post("$VERSION_V1_PREFIX/create_user") {
            val newUser = call.receive<NewUser>()
            val result = userPort.createUser(newUser)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}