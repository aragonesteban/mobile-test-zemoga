package com.zemoga.domain.repository

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem

interface CommentsRepository {
    suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<CommentItem>>
}