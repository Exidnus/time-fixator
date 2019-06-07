package ru.dvvar.time.fix.port.da

import ru.dvvar.time.fix.bl.BooleanResult
import ru.dvvar.time.fix.port.rest.ChangeActivity
import ru.dvvar.time.fix.port.rest.RenameActivity
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock

interface ActivityDa {
    fun saveActivity(changeActivity: ChangeActivity): BooleanResult
    fun removeActivity(changeActivity: ChangeActivity): BooleanResult
    fun renameActivity(renameActivity: RenameActivity): BooleanResult
}

class ActivityDaInMemory : ActivityDa {
    private val usersToActivities: MutableMap<Int, Pair<MutableSet<String>, Lock>> = ConcurrentHashMap()

    override fun saveActivity(changeActivity: ChangeActivity): BooleanResult {
        val (activities, _) = usersToActivities[changeActivity.userId] ?: return BooleanResult.fail()
        return BooleanResult(activities.add(changeActivity.activityName))
    }

    override fun removeActivity(changeActivity: ChangeActivity): BooleanResult {
        val (activities, _) = usersToActivities[changeActivity.userId] ?: return BooleanResult.fail()
        return BooleanResult(activities.remove(changeActivity.activityName))
    }

    override fun renameActivity(renameActivity: RenameActivity): BooleanResult {
        val (activities, lock) = usersToActivities[renameActivity.userId] ?: return BooleanResult.fail()
        if (!lock.tryLock(5, TimeUnit.SECONDS)) {
            //TODO add logging
            return BooleanResult.fail()
        }

        try {
            if (!activities.remove(renameActivity.oldActivityName)) {
                return BooleanResult.fail()
            }
            if (!activities.add(renameActivity.newActivityName)) {
                activities.add(renameActivity.oldActivityName)
                return BooleanResult.fail()
            }
            return BooleanResult.success()
        } finally {
            lock.unlock()
        }
    }
}