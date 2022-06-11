package com.zemoga.data.remote.users

import com.zemoga.data.remote.api.users.UsersApi
import com.zemoga.data.remote.api.executeRetrofitRequest
import com.zemoga.data.remote.api.handleResultRetrofit
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User

class RemoteUsersImpl(
    private val usersApi: UsersApi
) : RemoteUsers {

    override suspend fun getUserById(userId: Int): ZemogaResult<User> {
        val result = executeRetrofitRequest {
            usersApi.getUserById(userId)
        }
        return handleResultRetrofit(result) {
            it.transformToUser()
        }
    }

}