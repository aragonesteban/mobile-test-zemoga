package com.zemoga.domain.usecases.posts

import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.repository.PostsRepository

class PostsUseCaseImpl(private val postsRepository: PostsRepository) : PostsUseCase {

    override suspend fun getPostsList(): ZemogaResult<List<PostItem>> {
        return postsRepository.getPostsList()
    }

    override suspend fun getPostById(postId: Int): ZemogaResult<PostItem> {
        return postsRepository.getPostById(postId)
    }

}