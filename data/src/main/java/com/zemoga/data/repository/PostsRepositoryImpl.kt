package com.zemoga.data.repository

import com.zemoga.data.remote.posts.RemotePosts
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.repository.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepositoryImpl(private val remotePosts: RemotePosts) : PostsRepository {

    override suspend fun getPostsList(): ZemogaResult<List<PostItem>> {
        return withContext(Dispatchers.IO) {
            remotePosts.getPostsList()
        }
    }

    override suspend fun getPostById(postId: Int, userId: Int): ZemogaResult<PostItem> {
        return withContext(Dispatchers.IO) {
            remotePosts.getPostById(postId)
        }
    }

}