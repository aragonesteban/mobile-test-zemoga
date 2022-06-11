package com.zemoga.data.remote.users

import com.zemoga.domain.ZemogaResult

interface RemoteUsers {
    suspend fun getUserById(userId: Int): ZemogaResult<Any>
}