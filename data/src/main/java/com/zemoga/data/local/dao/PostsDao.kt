package com.zemoga.data.local.dao

import androidx.room.*
import com.zemoga.data.local.entities.PostEntity
import com.zemoga.domain.model.PostItem

@Dao
interface PostsDao {
    @Insert
    fun insertAllPosts(postsList: List<PostEntity>)

    @Update
    fun updatePost(postItem: PostEntity)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE id = :postId")
    fun getPostById(postId: Int): PostEntity?

    @Query("DELETE FROM posts")
    fun deleteAllPosts()
}
