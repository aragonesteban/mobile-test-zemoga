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

    override suspend fun getPostsList(loadPostsFromApi: Boolean): ZemogaResult<List<PostItem>> {
        return withContext(Dispatchers.IO) {
            val postsList = localPosts.getAllPosts()
            if (postsList.isNotEmpty() && loadPostsFromApi.not()) {
                ZemogaResult.Success(postsList)
            } else {
                val result = remotePosts.getPostsList()
                if (result is ZemogaResult.Success) {
                    localPosts.insertAllPosts(result.data)
                }
                if (loadPostsFromApi) {
                    ZemogaResult.Success(localPosts.getAllPosts())
                } else {
                    result
                }
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

    override suspend fun updatePost(postItem: PostItem) {
        withContext(Dispatchers.IO) {
            localPosts.updatePost(postItem)
        }
    }

    override suspend fun deletePost(postId: Int) {
        withContext(Dispatchers.IO) {
            localPosts.deletePost(postId)
        }
    }

}