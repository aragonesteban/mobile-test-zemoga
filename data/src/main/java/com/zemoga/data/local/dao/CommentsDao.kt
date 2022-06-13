package com.zemoga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zemoga.data.local.entities.CommentEntity

@Dao
interface CommentsDao{

    @Insert
    fun insertComments(commentsList: List<CommentEntity>)

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    fun getCommentsByPostId(postId: Int): List<CommentEntity>

}