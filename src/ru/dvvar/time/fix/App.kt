package ru.dvvar.time.fix

import ru.dvvar.time.fix.port.da.UserDaInMemory
import ru.dvvar.time.fix.port.rest.UserPortAdapter
import ru.dvvar.time.fix.transport.UserServer

fun main() {
    val userServer = UserServer(UserPortAdapter(UserDaInMemory()))
    userServer.createAndStartServer()
}