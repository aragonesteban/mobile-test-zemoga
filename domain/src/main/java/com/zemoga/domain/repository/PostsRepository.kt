package com.zemoga.domain.repository

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem

interface PostsRepository {
    suspend fun getPostsList(): ZemogaResult<List<PostItem>>
    suspend fun getPostById(postId: Int, userId: Int): ZemogaResult<PostItem>
}