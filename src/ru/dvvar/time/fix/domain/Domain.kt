package ru.dvvar.time.fix.domain

import java.time.LocalDate
import java.util.*

data class Activity(val name: String, val minutesInDate: SortedMap<LocalDate, Minute>)

data class User(val id: Int, val username: String, val activities: Set<Activity>) {
    constructor(id: Int, username: String) : this(id, username, setOf())
}

typealias Minute = Int

fun changeUsername(user: User, newUserName: String): User =
    User(user.id, newUserName, user.activities)

fun addActivity(user: User, activity: Activity): User =
        changeActivities(user) { it + activity }

fun removeActivity(user: User, activity: Activity): User =
    changeActivities(user) { it - activity }

fun addTimeToActivity(activity: Activity, date: LocalDate, minutes: Minute): Activity {
    val mapCopy = TreeMap<LocalDate, Minute>(activity.minutesInDate)
    mapCopy.merge(date, minutes) { old, new -> old + new }
    return Activity(activity.name, mapCopy)
}

private fun changeActivities(user: User, change: (Set<Activity>) -> Set<Activity>): User =
    User(user.id, user.username, change(user.activities))
