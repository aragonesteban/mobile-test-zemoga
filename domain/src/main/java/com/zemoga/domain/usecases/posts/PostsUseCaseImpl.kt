package com.zemoga.domain.usecases.posts

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.repository.PostsRepository

class PostsUseCaseImpl(private val postsRepository: PostsRepository) : PostsUseCase {

    override suspend fun getPostsList(loadPostsFromApi: Boolean): ZemogaResult<List<PostItem>> {
        val result = postsRepository.getPostsList(loadPostsFromApi)
        return if (result is ZemogaResult.Success) {
            val newPostsList = result.data.sortedByDescending { post -> post.isFavorite }
            ZemogaResult.Success(newPostsList)
        } else {
            result
        }
    }

    override suspend fun getPostById(postId: Int): ZemogaResult<PostItem> {
        return postsRepository.getPostById(postId)
    }

    override suspend fun deleteAllPost() {
        postsRepository.deleteAllPost()
    }

    override suspend fun updatePost(postItem: PostItem) {
        postsRepository.updatePost(postItem)
    }

    override suspend fun deletePost(postId: Int) {
        postsRepository.deletePost(postId)
    }

    override fun filterFavoritePostsList(postsList: List<PostItem>): List<PostItem> {
        return postsList.filter { postItem -> postItem.isFavorite }
    }

}