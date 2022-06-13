package com.zemoga.data.remote.comments

import com.zemoga.data.remote.api.comments.CommentsApi
import com.zemoga.data.remote.api.executeRetrofitRequest
import com.zemoga.data.remote.api.handleResultRetrofit
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem

class RemoteCommentsImpl(
    private val commentsApi: CommentsApi
) : RemoteComments {

    override suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<CommentItem>> {
        val result = executeRetrofitRequest {
            commentsApi.getCommentsByPostId(postId)
        }
        return handleResultRetrofit(result) {
            it.transformToCommentsList()
        }
    }

}