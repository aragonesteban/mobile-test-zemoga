package com.zemoga.data.remote.posts

import com.zemoga.domain.ZemogaResult

interface RemotePosts {
    suspend fun getPostsList(): ZemogaResult<List<Any>>
    suspend fun getPostById(postId: Int): ZemogaResult<Any>
}