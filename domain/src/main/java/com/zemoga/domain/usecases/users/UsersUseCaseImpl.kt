package com.zemoga.domain.usecases.users

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User
import com.zemoga.domain.repository.UsersRepository

class UsersUseCaseImpl(private val usersRepository: UsersRepository) : UsersUseCase {

    override suspend fun getUserById(userId: Int): ZemogaResult<User> {
        return usersRepository.getUSerById(userId)
    }

}