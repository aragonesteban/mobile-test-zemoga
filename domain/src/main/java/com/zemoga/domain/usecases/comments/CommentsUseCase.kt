package com.zemoga.domain.usecases.comments

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem

interface CommentsUseCase {
    suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<CommentItem>>
}