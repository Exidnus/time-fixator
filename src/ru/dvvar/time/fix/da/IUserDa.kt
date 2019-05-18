package ru.dvvar.time.fix.da

import ru.dvvar.time.fix.bl.Result
import ru.dvvar.time.fix.domain.User

interface IUserDa {
    fun createUser(username: String): Result<User>
}