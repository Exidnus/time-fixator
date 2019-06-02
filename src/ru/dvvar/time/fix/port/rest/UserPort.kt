package ru.dvvar.time.fix.port.rest

import ru.dvvar.time.fix.bl.Result
import ru.dvvar.time.fix.domain.User
import ru.dvvar.time.fix.port.da.IUserDa

data class NewUser(val username: String)

interface UserPort {
    fun createUser(newUser: NewUser): Result<User>
}

class UserPortAdapter(private val userDa: IUserDa) : UserPort {
    override fun createUser(newUser: NewUser): Result<User> = userDa.createUser(newUser.username)
}