package com.zemoga.data.remote.users

import com.zemoga.data.remote.api.users.UsersResponse
import com.zemoga.domain.DEFAULT_INT
import com.zemoga.domain.model.User

fun UsersResponse.transformToUser(): User {
    return User(
        id = id ?: DEFAULT_INT,
        name = name ?: String(),
        email = email ?: String(),
        phone = phone ?: String(),
        website = website ?: String(),
    )
}