package ru.dvvar.time.fix.transport

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.post
import io.ktor.routing.put
import ru.dvvar.time.fix.port.rest.ActivityPort
import ru.dvvar.time.fix.port.rest.ChangeActivity
import ru.dvvar.time.fix.port.rest.RenameActivity

class ActivityServer(private val activityPort: ActivityPort) : AbstractServer() {
    override fun routingConfiguration(): Routing.() -> Unit = {
        post("$VERSION_V1_PREFIX/create_activity") {
            val newActivity = call.receive<ChangeActivity>()
            val result = activityPort.addActivity(newActivity)
            call.respond(HttpStatusCode.OK, result)
        }
        delete("$VERSION_V1_PREFIX/delete_activity") {
            val activityForDelete = call.receive<ChangeActivity>()
            val result = activityPort.removeActivity(activityForDelete)
            call.respond(HttpStatusCode.OK, result)
        }
        put("$VERSION_V1_PREFIX/rename_activity") {
            val renamingActivity = call.receive<RenameActivity>()
            val result = activityPort.renameActivity(renamingActivity)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}