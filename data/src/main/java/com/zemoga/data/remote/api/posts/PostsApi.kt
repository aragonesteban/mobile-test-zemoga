package com.zemoga.data.remote.api.posts

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val POSTS_SLUG = "posts"
internal const val POST_ID = "postId"

interface PostsApi {

    @GET("$POSTS_SLUG/")
    suspend fun getPostsList(): Response<List<PostsResponse>>

    @GET("$POSTS_SLUG/{$POST_ID}")
    suspend fun getPostById(@Path(POST_ID) postId: Int): Response<PostsResponse>

    companion object {
        fun buildPostsApi(retrofit: Retrofit): PostsApi = retrofit.create(PostsApi::class.java)
    }

}