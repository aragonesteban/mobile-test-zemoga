package com.zemoga.data.remote.users

import com.zemoga.data.remote.api.users.UsersApi
import com.zemoga.data.remote.api.executeRetrofitRequest
import com.zemoga.domain.ZemogaResult

class RemoteUsersImpl(
    private val usersApi: UsersApi
) : RemoteUsers {

    override suspend fun getUserById(userId: Int): ZemogaResult<Any> {
        val result = executeRetrofitRequest {
            usersApi.getUserById(userId)
        }
        return ZemogaResult.Success(Unit)
    }

}