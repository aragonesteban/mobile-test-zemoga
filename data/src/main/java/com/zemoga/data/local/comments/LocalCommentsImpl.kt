package com.zemoga.data.local.comments

import com.zemoga.data.local.dao.CommentsDao
import com.zemoga.data.local.entities.CommentEntity
import com.zemoga.data.local.entities.transformToCommentsList
import com.zemoga.domain.model.CommentItem

class LocalCommentsImpl(
    private val commentsDao: CommentsDao
) : LocalComments {

    override fun insertComments(commentsList: List<CommentItem>) {
        val commentsEntities = commentsList.map { comment ->
            CommentEntity(
                id = comment.id,
                postId = comment.postId,
                body = comment.body,
                email = comment.email,
                name = comment.name,
            )
        }
        commentsDao.insertComments(commentsEntities)
    }

    override fun getCommentsByPostId(postId: Int): List<CommentItem> {
        return commentsDao.getCommentsByPostId(postId).transformToCommentsList()
    }

}