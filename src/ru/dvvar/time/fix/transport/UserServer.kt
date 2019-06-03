package ru.dvvar.time.fix.transport

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.post
import io.ktor.routing.put
import ru.dvvar.time.fix.port.rest.NewUser
import ru.dvvar.time.fix.port.rest.RemovingUser
import ru.dvvar.time.fix.port.rest.RenamingUser
import ru.dvvar.time.fix.port.rest.UserPort

class UserServer(private val userPort: UserPort) : AbstractServer() {
    override fun routingConfiguration(): Routing.() -> Unit = {
        post("$VERSION_V1_PREFIX/create_user") {
            val newUser = call.receive<NewUser>()
            val result = userPort.createUser(newUser)
            call.respond(HttpStatusCode.OK, result)
        }
        put("$VERSION_V1_PREFIX/rename_user") {
            val renamingUser = call.receive<RenamingUser>()
            val result = userPort.renameUser(renamingUser)
            call.respond(HttpStatusCode.OK, result)
        }
        delete("$VERSION_V1_PREFIX/delete_user") {
            val removingUser = call.receive<RemovingUser>()
            val result = userPort.removeUser(removingUser)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}