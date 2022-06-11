package com.zemoga.data.repository

import com.zemoga.data.remote.users.RemoteUsers
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User
import com.zemoga.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepositoryImpl(private val remoteUsers: RemoteUsers) : UsersRepository {

    override suspend fun getUSerById(userId: Int): ZemogaResult<User> {
        return withContext(Dispatchers.IO) {
            remoteUsers.getUserById(userId)
        }
    }

}