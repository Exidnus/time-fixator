package ru.dvvar.time.fix.port.da

import ru.dvvar.time.fix.bl.BooleanResult
import ru.dvvar.time.fix.port.rest.ChangeActivity
import ru.dvvar.time.fix.port.rest.FixateTime
import ru.dvvar.time.fix.port.rest.RenameActivity
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock

interface ActivityDa {
    fun saveActivity(changeActivity: ChangeActivity): BooleanResult
    fun removeActivity(changeActivity: ChangeActivity): BooleanResult
    fun renameActivity(renameActivity: RenameActivity): BooleanResult
    fun fixateTime(fixateTime: FixateTime): BooleanResult
}

class ActivityDaInMemory : ActivityDa {
    private val usersToActivities: MutableMap<Int, Pair<MutableMap<String, Int>, Lock>> = ConcurrentHashMap()

    override fun saveActivity(changeActivity: ChangeActivity): BooleanResult {
        val (activities, _) = usersToActivities[changeActivity.userId] ?: return BooleanResult.fail()
        val previousValue = activities.putIfAbsent(changeActivity.activityName, 0)
        return BooleanResult(previousValue == null)
    }

    override fun removeActivity(changeActivity: ChangeActivity): BooleanResult {
        val (activities, _) = usersToActivities[changeActivity.userId] ?: return BooleanResult.fail()
        return BooleanResult(activities.remove(changeActivity.activityName) != null)
    }

    override fun renameActivity(renameActivity: RenameActivity): BooleanResult {
        val (activities, lock) = usersToActivities[renameActivity.userId] ?: return BooleanResult.fail()
        if (!lock.tryLock(5, TimeUnit.SECONDS)) {
            //TODO add logging
            return BooleanResult.fail()
        }

        try {
            val minutesForOldActivityName = activities[renameActivity.oldActivityName] ?: return BooleanResult.fail()
            val previousValue = activities.putIfAbsent(renameActivity.newActivityName, minutesForOldActivityName)
            return if (previousValue == null) {
                activities.remove(renameActivity.oldActivityName)
                BooleanResult.success()
            } else {
                BooleanResult.fail()
            }
        } finally {
            lock.unlock()
        }
    }

    override fun fixateTime(fixateTime: FixateTime): BooleanResult {
        val (activities, _) = usersToActivities[fixateTime.userId] ?: return BooleanResult.fail()
        val newValue = activities.computeIfPresent(fixateTime.activityName) { _, old -> old + fixateTime.minutes }
        return BooleanResult(newValue != null)
    }
}