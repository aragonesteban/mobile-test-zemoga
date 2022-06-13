package com.zemoga.data.repository

import com.zemoga.data.local.users.LocalUsers
import com.zemoga.data.remote.users.RemoteUsers
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User
import com.zemoga.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepositoryImpl(
    private val localUsers: LocalUsers,
    private val remoteUsers: RemoteUsers
) : UsersRepository {

    override suspend fun getUserById(userId: Int): ZemogaResult<User> {
        return withContext(Dispatchers.IO) {
            val user = localUsers.getUserById(userId)
            user?.let {
                ZemogaResult.Success(user)
            } ?: run {
                val result = remoteUsers.getUserById(userId)
                if (result is ZemogaResult.Success) {
                    localUsers.insertUser(result.data)
                }
                result
            }
        }
    }

}