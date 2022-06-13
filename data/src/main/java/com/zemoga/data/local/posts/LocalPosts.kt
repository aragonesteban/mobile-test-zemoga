package com.zemoga.data.local.posts

import com.zemoga.domain.model.PostItem

interface LocalPosts {
    fun getAllPosts(): List<PostItem>
    fun insertAllPosts(postsList: List<PostItem>)
    fun updateAllPosts(postsList: List<PostItem>)
    fun deleteAllPosts()
    fun getPostById(postId: Int): PostItem?
    fun updatePost(postItem: PostItem)
    fun deletePost(postId: Int)
}