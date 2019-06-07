package ru.dvvar.time.fix.bl

import ru.dvvar.time.fix.domain.User
import ru.dvvar.time.fix.port.da.UserDa

data class Result<T>(val isSuccess: Boolean, val obj: T?, val msg: String?) {
    constructor(isSuccess: Boolean, obj: T?) : this(isSuccess, obj, null)
}

data class BooleanResult(val isSuccess: Boolean) {
    companion object {
        fun success() = BooleanResult(true)
        fun fail() = BooleanResult(false)
    }
}

interface IUserService {
    fun createUser(username: String): Result<User>
}

class UserService(userDa: UserDa) : IUserService {
    override fun createUser(username: String): Result<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}