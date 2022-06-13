package com.zemoga.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.repository.PostsRepository
import com.zemoga.domain.usecases.posts.PostsUseCase
import com.zemoga.domain.usecases.posts.PostsUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PostsUseCaseTest {

    private val postId = 1
    private val mockPostItem = PostItem(
        userId = 1,
        id = postId,
        title = "test",
        body = "test",
        isFavorite = true,
    )
    private val mockPostsList = listOf(mockPostItem)

    private val mockPostsRepository = mock<PostsRepository> {
        onBlocking { getPostsList(true) } doReturn ZemogaResult.Success(mockPostsList)
        onBlocking { getPostById(postId) } doReturn ZemogaResult.Success(mockPostItem)
        onBlocking { deleteAllPost() } doReturn Unit
        onBlocking { updatePost(mockPostItem) } doReturn Unit
        onBlocking { deletePost(postId) } doReturn Unit
    }

    private lateinit var useCase: PostsUseCase

    @Before
    fun setUp() {
        useCase = PostsUseCaseImpl(mockPostsRepository)
    }

    @Test
    fun `getPostsList() verify that getPostsList() is calling from PostsRepository`() {
        runTest {
            useCase.getPostsList(true)
            verify(mockPostsRepository).getPostsList(true)
        }
    }

    @Test
    fun `getPostById() verify that getPostById() is calling from PostsRepository`() {
        runTest {
            useCase.getPostById(postId)
            verify(mockPostsRepository).getPostById(postId)
        }
    }

    @Test
    fun `deleteAllPost() verify that deleteAllPost() is calling from PostsRepository`() {
        runTest {
            useCase.deleteAllPost()
            verify(mockPostsRepository).deleteAllPost()
        }
    }

    @Test
    fun `updatePost() verify that updatePost() is calling from PostsRepository`() {
        runTest {
            useCase.updatePost(mockPostItem)
            verify(mockPostsRepository).updatePost(mockPostItem)
        }
    }

    @Test
    fun `deletePost() verify that deletePost() is calling from PostsRepository`() {
        runTest {
            useCase.deletePost(postId)
            verify(mockPostsRepository).deletePost(postId)
        }
    }

    @Test
    fun `getPostsList() when repository returns ZemogaResult Success then use case returns a ZemogaResult Success`() {
        runBlocking {
            val result = useCase.getPostsList(true)
            assertTrue(result is ZemogaResult.Success)
        }
    }

    @Test
    fun `getPostById() when repository returns ZemogaResult Success then use case returns a ZemogaResult Success`() {
        runTest {
            val result = useCase.getPostById(postId)
            assertTrue(result is ZemogaResult.Success)
        }
    }

}