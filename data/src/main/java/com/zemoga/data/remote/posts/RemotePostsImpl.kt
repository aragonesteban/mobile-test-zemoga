package com.zemoga.data.remote.posts

import com.zemoga.data.remote.api.posts.PostsApi
import com.zemoga.data.remote.api.executeRetrofitRequest
import com.zemoga.domain.ZemogaResult

class RemotePostsImpl(private val postsApi: PostsApi) : RemotePosts {

    override suspend fun getPostsList(): ZemogaResult<List<Any>> {
        val result = executeRetrofitRequest {
            postsApi.getPostsList()
        }
        return ZemogaResult.Success(listOf())
    }

    override suspend fun getPostById(postId: Int): ZemogaResult<Any> {
        val result = executeRetrofitRequest {
            postsApi.getPostById(postId)
        }
        return ZemogaResult.Success(Unit)
    }

}