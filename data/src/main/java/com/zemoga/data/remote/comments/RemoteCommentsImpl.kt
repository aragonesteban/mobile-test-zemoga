package com.zemoga.data.remote.comments

import com.zemoga.data.remote.api.comments.CommentsApi
import com.zemoga.data.remote.api.executeRetrofitRequest
import com.zemoga.domain.ZemogaResult

class RemoteCommentsImpl(
    private val commentsApi: CommentsApi
) : RemoteComments {

    override suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<Any>> {
        val result =  executeRetrofitRequest {
            commentsApi.getCommentsByPostId(postId)
        }
        return ZemogaResult.Success(listOf())
    }

}