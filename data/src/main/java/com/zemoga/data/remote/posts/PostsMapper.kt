package com.zemoga.data.remote.posts

import com.zemoga.data.remote.api.posts.PostsResponse
import com.zemoga.domain.DEFAULT_INT
import com.zemoga.domain.model.PostItem

fun List<PostsResponse>.transformToPostsList(): List<PostItem> {
    return map { post ->
        post.transformToPostItem()
    }
}

fun PostsResponse.transformToPostItem(): PostItem {
    return PostItem(
        userId = userId ?: DEFAULT_INT,
        id = id ?: DEFAULT_INT,
        title = title ?: String(),
        body = body ?: String(),
    )
}