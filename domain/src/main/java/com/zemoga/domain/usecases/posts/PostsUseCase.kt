package com.zemoga.domain.usecases.posts

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem

interface PostsUseCase {
    suspend fun getPostsList(): ZemogaResult<List<PostItem>>
    suspend fun getPostById(postId: Int): ZemogaResult<PostItem>
}