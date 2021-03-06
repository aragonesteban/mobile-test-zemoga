package com.zemoga.domain.repository

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem

interface PostsRepository {
    suspend fun getPostsList(loadPostsFromApi: Boolean): ZemogaResult<List<PostItem>>
    suspend fun getPostById(postId: Int): ZemogaResult<PostItem>
    suspend fun deleteAllPost()
    suspend fun updatePost(postItem: PostItem)
    suspend fun deletePost(postId: Int)
}