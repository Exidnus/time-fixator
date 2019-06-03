package ru.dvvar.time.fix.port.da

import ru.dvvar.time.fix.bl.BooleanResult
import ru.dvvar.time.fix.bl.Result
import ru.dvvar.time.fix.domain.User
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

interface UserDa {
    fun createUser(username: String): Result<User>
    fun findUser(id: Int): User?
    fun removeUser(id: Int): BooleanResult
    fun updateUser(user: User): BooleanResult
}

class UserDaInMemory : UserDa {
    private val users: MutableMap<Int, User> = ConcurrentHashMap()
    private val idSeq: AtomicInteger = AtomicInteger()

    override fun createUser(username: String): Result<User> {
        val id = idSeq.getAndIncrement()
        val user = User(id, username)
        users[id] = User(id, username)
        return Result(true, user)
    }

    override fun findUser(id: Int): User? = users[id]

    override fun removeUser(id: Int): BooleanResult = BooleanResult(users.remove(id) != null)

    override fun updateUser(user: User): BooleanResult {
        val wasUpdated = users.computeIfPresent(user.id) { _, _ -> user } != null
        return BooleanResult(wasUpdated)
    }
}