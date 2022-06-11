package com.zemoga.domain.model

data class CommentItem(
    val id: Int,
    val postId: Int,
    val body: String,
    val email: String,
    val name: String
)