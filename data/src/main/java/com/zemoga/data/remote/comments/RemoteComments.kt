package com.zemoga.data.remote.comments

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem

interface RemoteComments {
    suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<CommentItem>>
}