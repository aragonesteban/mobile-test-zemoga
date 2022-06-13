package com.zemoga.data.local.posts

import com.zemoga.data.local.dao.PostsDao
import com.zemoga.data.local.entities.PostEntity
import com.zemoga.data.local.entities.transformToPostsList
import com.zemoga.domain.model.PostItem

class LocalPostsImpl(
    private val postsDao: PostsDao
) : LocalPosts {

    override fun getAllPosts(): List<PostItem> {
        return postsDao.getAllPosts().transformToPostsList()
    }

    override fun getPostById(postId: Int): PostItem? {
        val result = postsDao.getPostById(postId)
        return result?.run {
            PostItem(
                id = id,
                userId = userId,
                title = title,
                body = body,
                isFavorite = isFavorite,
            )
        }
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