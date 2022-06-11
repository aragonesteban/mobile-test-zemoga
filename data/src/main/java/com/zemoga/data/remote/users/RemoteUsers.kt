package com.zemoga.data.remote.users

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User

interface RemoteUsers {
    suspend fun getUserById(userId: Int): ZemogaResult<User>
}