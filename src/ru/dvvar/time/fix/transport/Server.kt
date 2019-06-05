package ru.dvvar.time.fix.transport

import io.ktor.application.install
import io.ktor.features.CORS
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
            install(CORS) {
                anyHost()
            }
            routing(routingConfiguration())
        }
        server.start(wait = true)
    }

    protected abstract fun routingConfiguration(): Routing.() -> Unit
}

