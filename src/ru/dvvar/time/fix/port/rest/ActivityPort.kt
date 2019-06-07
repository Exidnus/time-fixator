package ru.dvvar.time.fix.port.rest

import ru.dvvar.time.fix.bl.BooleanResult
import ru.dvvar.time.fix.port.da.ActivityDa

data class ChangeActivity(val userId: Int, val activityName: String)
data class RenameActivity(val userId: Int, val oldActivityName: String, val newActivityName: String)
data class FixateTime(val userId: Int, val activityName: String, val minutes: Int)

interface ActivityPort {
    fun addActivity(changeActivity: ChangeActivity): BooleanResult
    fun removeActivity(changeActivity: ChangeActivity): BooleanResult
    fun renameActivity(renameActivity: RenameActivity): BooleanResult
    fun fixateTime(fixateTime: FixateTime): BooleanResult
}

class ActivityPortAdapter(private val activityDa: ActivityDa) : ActivityPort {
    override fun addActivity(changeActivity: ChangeActivity): BooleanResult = activityDa.saveActivity(changeActivity)

    override fun removeActivity(changeActivity: ChangeActivity): BooleanResult = activityDa.removeActivity(changeActivity)

    override fun renameActivity(renameActivity: RenameActivity): BooleanResult = activityDa.renameActivity(renameActivity)

    override fun fixateTime(fixateTime: FixateTime): BooleanResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}