package ru.dvvar.time.fix.port.rest

import ru.dvvar.time.fix.bl.BooleanResult

data class ChangeActivity(val userId: Int, val activityName: String)
data class RenameActivity(val userId: Int, val oldActivityName: String, val newActivityName: String)
data class FixateTime(val userId: Int, val activityName: String, val minutes: Int)

interface ActivityPort {
    fun addActivity(changeActivity: ChangeActivity): BooleanResult
    fun removeActivity(changeActivity: ChangeActivity): BooleanResult
    fun renameActivity(renameActivity: RenameActivity): BooleanResult
    fun fixateTime(fixateTime: FixateTime): BooleanResult
}