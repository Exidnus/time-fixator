package ru.dvvar.time.fix.transport

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ru.dvvar.time.fix.da.IUserDa

const val VERSTION_PREFIX = "/v1"

data class NewUser(val username: String)
data class ChangeActivity(val userId: Int, val activityName: String)

class Server(private val userDa: IUserDa) {

    fun createAndStartServer() {
        val server = embeddedServer(Netty, port = 4444) {
            install(ContentNegotiation) {
                jackson {

                }
            }
            routing {
                //            get("/") { call.respondText("Hello, world!", ContentType.Text.Plain) }
                post("$VERSTION_PREFIX/create_user") {
                    val newUser = call.receive<NewUser>()
                    val result = userDa.createUser(newUser.username)
                    call.respond(HttpStatusCode.OK, result)
                }
                post("$VERSTION_PREFIX/add_activity") {
                }
                post("$VERSTION_PREFIX/remove_activity") {
                    val changeActivity = call.receive<ChangeActivity>()

                }
            }
        }
        server.start(wait = true)
    }
}

