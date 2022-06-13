package com.zemoga.domain.usecases.users

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User

interface UsersUseCase {
    suspend fun getUserById(userId: Int): ZemogaResult<User>
}