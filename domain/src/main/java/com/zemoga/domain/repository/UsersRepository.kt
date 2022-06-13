package com.zemoga.domain.repository

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User

interface UsersRepository {
    suspend fun getUserById(userId: Int): ZemogaResult<User>
}