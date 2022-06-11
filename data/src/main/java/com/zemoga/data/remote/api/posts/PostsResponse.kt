package com.zemoga.data.remote.api.posts

import kotlinx.serialization.Serializable

@Serializable
data class PostsResponse(
    val userId: Int? = null,
    val id: Int? = null,
    val title: String? = String(),
    val body: String? = String()
)