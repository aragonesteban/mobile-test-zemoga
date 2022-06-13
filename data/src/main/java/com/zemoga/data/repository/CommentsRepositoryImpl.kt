package com.zemoga.data.repository

import com.zemoga.data.local.comments.LocalComments
import com.zemoga.data.remote.comments.RemoteComments
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentsRepositoryImpl(
    private val localComments: LocalComments,
    private val remoteComments: RemoteComments
) : CommentsRepository {

    override suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<CommentItem>> {
        return withContext(Dispatchers.IO) {
            val commentsList = localComments.getCommentsByPostId(postId)
            if (commentsList.isNotEmpty()) {
                ZemogaResult.Success(commentsList)
            } else {
                val result = remoteComments.getCommentsByPostId(postId)
                if (result is ZemogaResult.Success) {
                    localComments.insertComments(result.data)
                }
                result
            }
        }
    }

}