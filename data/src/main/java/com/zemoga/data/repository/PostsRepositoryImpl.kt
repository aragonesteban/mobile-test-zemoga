package com.zemoga.data.repository

import com.zemoga.data.local.posts.LocalPosts
import com.zemoga.data.remote.posts.RemotePosts
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.repository.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepositoryImpl(
    private val localPosts: LocalPosts,
    private val remotePosts: RemotePosts
) : PostsRepository {

    override suspend fun getPostsList(): ZemogaResult<List<PostItem>> {
        return withContext(Dispatchers.IO) {
            val postsList = localPosts.getAllPosts()
            if (postsList.isNotEmpty()) {
                ZemogaResult.Success(postsList)
            } else {
                val result = remotePosts.getPostsList()
                if (result is ZemogaResult.Success) {
                    localPosts.insertAllPosts(result.data)
                }
                result
            }
        }
    }

    override suspend fun getPostById(postId: Int): ZemogaResult<PostItem> {
        return withContext(Dispatchers.IO) {
            val postItem = localPosts.getPostById(postId)
            postItem?.let {
                ZemogaResult.Success(postItem)
            } ?: run {
                remotePosts.getPostById(postId)
            }
        }
    }

    override suspend fun deleteAllPost() {
        withContext(Dispatchers.IO) {
            localPosts.deleteAllPosts()
        }
    }

}