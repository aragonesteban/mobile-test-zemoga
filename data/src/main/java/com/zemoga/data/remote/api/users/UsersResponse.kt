package com.zemoga.data.remote.api.users

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val id: Int? = null,
    val name: String? = String(),
    val email: String? = String(),
    val phone: String? = String(),
    val website: String? = String()
)