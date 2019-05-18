package ru.dvvar.time.fix.domain

typealias Activity = String

data class User(val id: Int, val username: String, val activities: Set<Activity>)

fun changeUsername(user: User, newUserName: String): User =
    User(user.id, newUserName, user.activities)

fun addActivity(user: User, activity: Activity): User =
    changeActivities(user) { it - activity }

fun removeActivity(user: User, activity: Activity): User =
    changeActivities(user) { it - activity }

private fun changeActivities(user: User, change: (Set<Activity>) -> Set<Activity>): User =
    User(user.id, user.username, change(user.activities))