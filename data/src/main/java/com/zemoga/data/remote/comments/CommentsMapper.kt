package com.zemoga.data.remote.comments

import com.zemoga.data.remote.api.comments.CommentsResponse
import com.zemoga.domain.DEFAULT_INT
import com.zemoga.domain.model.CommentItem

fun List<CommentsResponse>.transformToCommentsList(): List<CommentItem> {
    return map { comment ->
        CommentItem(
            id = comment.id ?: DEFAULT_INT,
            postId = comment.postId ?: DEFAULT_INT,
            body = comment.body ?: String(),
            email = comment.email ?: String(),
            name = comment.name ?: String()
        )
    }
}