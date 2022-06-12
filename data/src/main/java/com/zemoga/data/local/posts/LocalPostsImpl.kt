package com.zemoga.data.local.posts

import com.zemoga.data.local.dao.PostsDao
import com.zemoga.data.local.entities.PostEntity
import com.zemoga.data.local.entities.transformToPostsList
import com.zemoga.domain.model.PostItem

class LocalPostsImpl(
    private val postsDao: PostsDao
) : LocalPosts {
    override fun getAllPosts(): List<PostItem> {
        val result = postsDao.getAllPosts()
        return result.transformToPostsList()
    }

    override fun getPostById(postId: Int): PostItem {
        return PostItem(
            1, 1, "", "", false
        )
    }

    override fun insertAllPosts(postsList: List<PostItem>) {
        val postsEntities = postsList.map { post ->
            PostEntity(
                id = post.id,
                userId = post.userId,
                title = post.title,
                body = post.body,
                isFavorite = post.isFavorite,
            )
        }
        postsDao.insertAllPosts(postsEntities)
    }

    override fun deleteAllPosts() {
        postsDao.deleteAllPosts()
    }
}