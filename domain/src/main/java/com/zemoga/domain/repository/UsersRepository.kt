package com.zemoga.domain.repository

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User

interface UsersRepository {
    suspend fun getUSerById(userId: Int): ZemogaResult<User>
}