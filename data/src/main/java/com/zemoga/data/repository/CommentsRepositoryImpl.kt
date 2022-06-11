package com.zemoga.data.repository

import com.zemoga.data.remote.comments.RemoteComments
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentsRepositoryImpl(private val remoteComments: RemoteComments) : CommentsRepository {

    override suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<CommentItem>> {
        return withContext(Dispatchers.IO) {
            remoteComments.getCommentsByPostId(postId)
        }
    }

}