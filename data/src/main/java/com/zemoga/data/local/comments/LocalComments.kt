package com.zemoga.data.local.comments

import com.zemoga.domain.model.CommentItem

interface LocalComments {
    fun insertComments(commentsList: List<CommentItem>)
    fun getCommentsByPostId(postId: Int): List<CommentItem>
}