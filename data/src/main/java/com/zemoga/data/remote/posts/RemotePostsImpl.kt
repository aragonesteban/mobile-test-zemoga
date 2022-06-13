package com.zemoga.data.remote.posts

import com.zemoga.data.remote.api.posts.PostsApi
import com.zemoga.data.remote.api.executeRetrofitRequest
import com.zemoga.data.remote.api.handleResultRetrofit
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem

class RemotePostsImpl(private val postsApi: PostsApi) : RemotePosts {

    override suspend fun getPostsList(): ZemogaResult<List<PostItem>> {
        val result = executeRetrofitRequest { postsApi.getPostsList() }
        return handleResultRetrofit(result) {
            it.transformToPostsList()
        }
    }

    override suspend fun getPostById(postId: Int): ZemogaResult<PostItem> {
        val result = executeRetrofitRequest { postsApi.getPostById(postId) }
        return handleResultRetrofit(result) {
            it.transformToPostItem()
        }
    }

}