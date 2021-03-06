package com.zemoga.domain.model

data class PostItem(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var isFavorite: Boolean = false
)