package com.zemoga.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zemoga.domain.model.CommentItem

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "post_id") val postId: Int,
    val body: String,
    val email: String,
    val name: String
)

fun List<CommentEntity>.transformToCommentsList(): List<CommentItem> {
    return map { comment ->
        CommentItem(
            id = comment.id,
            postId = comment.postId,
            body = comment.body,
            email = comment.email,
            name = comment.name,
        )
    }
}