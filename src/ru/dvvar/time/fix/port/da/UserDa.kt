package ru.dvvar.time.fix.port.da

import ru.dvvar.time.fix.bl.Result
import ru.dvvar.time.fix.domain.User
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

interface UserDa {
    fun createUser(username: String): Result<User>
    fun removeUser(id: Int): Result<Boolean>
    fun renameUser(id: Int, newName: String): Result<Boolean>
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

    override fun removeUser(id: Int): Result<Boolean> {
        users.remove(id) != null
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun renameUser(id: Int, newName: String): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}