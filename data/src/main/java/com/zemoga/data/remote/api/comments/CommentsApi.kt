package com.zemoga.data.remote.api.comments

import com.zemoga.data.remote.api.posts.POST_ID
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val COMMENTS_SLUG = "comments"

interface CommentsApi {

    @GET("$COMMENTS_SLUG/")
    fun getCommentsByPostId(@Query(POST_ID) postId: Int): Response<List<CommentsResponse>>

    companion object {
        fun buildCommentsApi(retrofit: Retrofit): CommentsApi =
            retrofit.create(CommentsApi::class.java)
    }

}