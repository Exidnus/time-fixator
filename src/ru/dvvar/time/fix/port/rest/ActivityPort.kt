package ru.dvvar.time.fix.port.rest

import ru.dvvar.time.fix.bl.Result

data class ChangeActivity(val userId: Int, val activityName: String)
data class RenameActivity(val userId: Int, val oldActivityName: String, val newActivityName: String)

interface ActivityPort {
    fun addActivity(changeActivity: ChangeActivity): Result<Boolean>
    fun removeActivity(changeActivity: ChangeActivity): Result<Boolean>
    fun renameActivity(renameActivity: RenameActivity): Result<Boolean>
}