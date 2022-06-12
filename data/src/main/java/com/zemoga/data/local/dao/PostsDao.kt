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

    @Query("SELECT * FROM post_entity")
    fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM post_entity WHERE id = :postId")
    fun getPostById(postId: Int): List<PostEntity>

    @Query("DELETE FROM post_entity")
    fun deleteAllPosts()
}
