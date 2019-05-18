package ru.dvvar.time.fix.bl

import ru.dvvar.time.fix.da.IUserDa
import ru.dvvar.time.fix.domain.User

data class Result<T>(val isSuccess: Boolean, val obj: T?, val msg: String?)

interface IUserService {
    fun createUser(username: String): Result<User>
}

class UserService(userDa: IUserDa) : IUserService {
    override fun createUser(username: String): Result<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}