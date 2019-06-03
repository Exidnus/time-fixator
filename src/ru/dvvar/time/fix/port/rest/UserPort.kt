package ru.dvvar.time.fix.port.rest

import ru.dvvar.time.fix.bl.BooleanResult
import ru.dvvar.time.fix.bl.Result
import ru.dvvar.time.fix.domain.User
import ru.dvvar.time.fix.port.da.UserDa

data class NewUser(val username: String)
data class RemovingUser(val id: Int)
data class RenamingUser(val id: Int, val newName: String)

interface UserPort {
    fun createUser(newUser: NewUser): Result<User>
    fun removeUser(removingUser: RemovingUser): BooleanResult
    fun renameUser(renamingUser: RenamingUser): BooleanResult
}

class UserPortAdapter(private val userDa: UserDa) : UserPort {
    override fun createUser(newUser: NewUser): Result<User> = userDa.createUser(newUser.username)
    override fun removeUser(removingUser: RemovingUser): BooleanResult = userDa.removeUser(removingUser.id)
    override fun renameUser(renamingUser: RenamingUser): BooleanResult =
        userDa.renameUser(renamingUser.id, renamingUser.newName)
}