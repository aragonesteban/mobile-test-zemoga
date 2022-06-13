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
        val postsEntities = transformToPostEntityList(postsList)
        postsDao.insertAllPosts(postsEntities)
    }

    override fun updateAllPosts(postsList: List<PostItem>) {
        val postsEntities = transformToPostEntityList(postsList)
        postsDao.updateAllPosts(postsEntities)
    }

    override fun updatePost(postItem: PostItem) {
        val postEntity = PostEntity(
            id = postItem.id,
            userId = postItem.userId,
            title = postItem.title,
            body = postItem.body,
            isFavorite = postItem.isFavorite,
        )
        postsDao.updatePost(postEntity)
    }

    override fun deletePost(postId: Int) {
        postsDao.deletePost(postId)
    }

    override fun deleteAllPosts() {
        postsDao.deleteAllPosts()
    }

    private fun transformToPostEntityList(postsList: List<PostItem>): List<PostEntity> {
        return postsList.map { post ->
            PostEntity(
                id = post.id,
                userId = post.userId,
                title = post.title,
                body = post.body,
                isFavorite = post.isFavorite,
            )
        }
    }

}