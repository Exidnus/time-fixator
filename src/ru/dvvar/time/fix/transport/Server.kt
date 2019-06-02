package ru.dvvar.time.fix.transport

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

const val VERSION_V1_PREFIX = "/v1"

abstract class AbstractServer {
    fun createAndStartServer() {
        val server = embeddedServer(Netty, port = 4444) {
            install(ContentNegotiation) {
                jackson {

                }
            }
            routing(routingConfiguration())
//            routing {
//                //            get("/") { call.respondText("Hello, world!", ContentType.Text.Plain) }
//                post("$VERSION_V1_PREFIX/create_user") {
//                    val newUser = call.receive<NewUser>()
//                    val result = userDa.createUser(newUser.username)
//                    call.respond(HttpStatusCode.OK, result)
//                }
//                post("$VERSION_V1_PREFIX/add_activity") {
//                }
//                post("$VERSION_V1_PREFIX/remove_activity") {
//                    val changeActivity = call.receive<ChangeActivity>()
//
//                }
//            }
        }
        server.start(wait = true)
    }

    protected abstract fun routingConfiguration(): Routing.() -> Unit
}

