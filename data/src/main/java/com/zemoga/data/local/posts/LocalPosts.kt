package com.zemoga.data.local.posts

import com.zemoga.domain.model.PostItem

interface LocalPosts {
    fun getAllPosts(): List<PostItem>
    fun getPostById(postId: Int): PostItem
    fun insertAllPosts(postsList: List<PostItem>)
    fun deleteAllPosts()
}