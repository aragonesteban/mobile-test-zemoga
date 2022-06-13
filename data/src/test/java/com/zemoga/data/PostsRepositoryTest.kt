package com.zemoga.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.data.local.posts.LocalPosts
import com.zemoga.data.remote.posts.RemotePosts
import com.zemoga.data.repository.PostsRepositoryImpl
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.repository.PostsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PostsRepositoryTest {

    private val postId = 1
    private val mockPostItem = PostItem(
        userId = 1,
        id = postId,
        title = "test",
        body = "test",
        isFavorite = true,
    )
    private val mockPostsList = listOf(mockPostItem)

    private val mockRemotePosts = mock<RemotePosts> {
        onBlocking { getPostsList() } doReturn ZemogaResult.Success(mockPostsList)
        onBlocking { getPostById(postId) } doReturn ZemogaResult.Success(mockPostItem)
    }

    private val mockLocalPosts = mock<LocalPosts> {
        onBlocking { getAllPosts() } doReturn mockPostsList
        onBlocking { getPostById(postId) } doReturn null
    }

    private lateinit var repository: PostsRepository

    @Before
    fun setUp() {
        repository = PostsRepositoryImpl(mockLocalPosts, mockRemotePosts)
    }

    @Test
    fun `getPostsList() verify that getPostsList() is calling from RemotePosts`() {
        runTest {
            repository.getPostsList(true)
            verify(mockRemotePosts).getPostsList()
        }
    }

    @Test
    fun `getPostsList() verify that getPostsList() is calling from LocalPosts`() {
        runTest {
            repository.getPostsList(true)
            verify(mockLocalPosts, times(2)).getAllPosts()
        }
    }

    @Test
    fun `getPostsList() when remote returns Zemoga Success then returns Success`() {
        runTest {
            val result = repository.getPostsList(true)
            assertTrue(result is ZemogaResult.Success)
        }
    }

    @Test
    fun `getPostById() verify that getPostById() is calling from RemotePosts`() {
        runTest {
            repository.getPostById(postId)
            verify(mockRemotePosts).getPostById(postId)
        }
    }

    @Test
    fun `getPostById() verify that getPostById() is calling from LocalPosts`() {
        runTest {
            repository.getPostById(postId)
            verify(mockLocalPosts).getPostById(postId)
        }
    }

    @Test
    fun `getPostById() when remote returns Zemoga Success then returns Success`() {
        runTest {
            val result = repository.getPostById(postId)
            assertTrue(result is ZemogaResult.Success)
        }
    }


}