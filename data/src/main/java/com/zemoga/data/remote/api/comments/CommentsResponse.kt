package com.zemoga.data.remote.api.comments

import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponse(
    val id: Int? = null,
    val postId: Int? = null,
    val body: String? = String(),
    val email: String? = String(),
    val name: String? = String(),
)

