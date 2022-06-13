package com.zemoga.domain.usecases.posts

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem

interface PostsUseCase {
    suspend fun getPostsList(loadPostsFromApi: Boolean): ZemogaResult<List<PostItem>>
    suspend fun getPostById(postId: Int): ZemogaResult<PostItem>
    suspend fun deleteAllPost()
    suspend fun updatePost(postItem: PostItem)
    suspend fun deletePost(postId: Int)
    fun filterFavoritePostsList(postsList: List<PostItem>): List<PostItem>
}