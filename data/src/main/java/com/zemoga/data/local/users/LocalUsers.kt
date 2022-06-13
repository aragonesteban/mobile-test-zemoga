package com.zemoga.data.local.users

import com.zemoga.domain.model.User

interface LocalUsers {
    fun insertUser(user: User)
    fun getUserById(userId: Int): User?
}