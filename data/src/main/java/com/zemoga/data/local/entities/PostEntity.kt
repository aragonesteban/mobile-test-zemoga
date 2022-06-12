package com.zemoga.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zemoga.domain.model.PostItem

@Entity(tableName = "post_entity")
data class PostEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    val title: String,
    val body: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false
)

fun List<PostEntity>.transformToPostsList(): List<PostItem> {
    return map { post ->
        PostItem(
            id = post.id,
            userId = post.userId,
            title = post.title,
            body = post.body,
            isFavorite = post.isFavorite
        )
    }
}