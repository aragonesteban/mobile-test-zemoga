package com.zemoga.data.remote.posts

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem

interface RemotePosts {
    suspend fun getPostsList(): ZemogaResult<List<PostItem>>
    suspend fun getPostById(postId: Int): ZemogaResult<PostItem>
}