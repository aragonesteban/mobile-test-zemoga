package com.zemoga.domain.usecases.comments

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.repository.CommentsRepository

class CommentsUseCaseImpl(
    private val commentsRepository: CommentsRepository
) : CommentsUseCase {

    override suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<CommentItem>> {
        return commentsRepository.getCommentsByPostId(postId)
    }

}