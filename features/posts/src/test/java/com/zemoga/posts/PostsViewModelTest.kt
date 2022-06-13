package com.zemoga.posts

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.usecases.posts.PostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PostsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()

    private val mockPostsList = listOf(
        PostItem(
            userId = 1,
            id = 1,
            title = "test",
            body = "test",
            isFavorite = true,
        )
    )

    private val mockPostsUseCase = mock<PostsUseCase> {
        onBlocking { getPostsList(true) } doReturn ZemogaResult.Success(mockPostsList)
        onBlocking { filterFavoritePostsList(mockPostsList) } doReturn mockPostsList
        onBlocking { deleteAllPost() } doReturn Unit
    }

    private lateinit var viewModel: PostsViewModel

    @Before
    fun setUp() {
        viewModel = PostsViewModel(mockPostsUseCase)
    }

    @Test
    fun `getAllPosts() verify that getPostsList() is calling from PostsUseCase`() {
        runTest {
            viewModel.getAllPosts(true)
            verify(mockPostsUseCase).getPostsList(true)
        }
    }

    @Test
    fun `getAllPosts() when use case returns ZemogaResult Success then state emit ShowAllPosts`() {
        runBlocking {
            viewModel.getAllPosts(true)
            assertTrue(viewModel.uiStateAllPosts.value is AllPostsUiState.ShowAllPosts)
        }
    }

    @Test
    fun `getAllPosts() verify that filterFavoritePostsList() is calling from PostsUseCase`() {
        runTest {
            viewModel.getAllPosts(true)
            verify(mockPostsUseCase).filterFavoritePostsList(mockPostsList)
        }
    }


    @Test
    fun `getAllPosts() when use case returns ZemogaResult Success then state emit ShowFavoritesPosts`() {
        runBlocking {
            viewModel.getAllPosts(true)
            assertTrue(viewModel.uiFavoritePosts.value is FavoritesPostsUiState.ShowFavoritesPosts)
        }
    }

    @Test
    fun `deleteAllPosts() verify that deleteAllPosts() is calling from PostsUseCase`() {
        runTest {
            viewModel.deleteAllPosts()
            verify(mockPostsUseCase).deleteAllPost()
        }
    }

    @Test
    fun `deleteAllPosts() when use case executes deleteAllPost() then state emit ShowEmptyPosts`() {
        runBlocking {
            viewModel.deleteAllPosts()
            assertTrue(viewModel.uiStateAllPosts.value is AllPostsUiState.ShowEmptyPosts)
        }
    }

    @Test
    fun `deleteAllPosts() when use case executes deleteAllPost() then state emit ShowEmptyFavoritesPosts`() {
        runBlocking {
            viewModel.deleteAllPosts()
            assertTrue(viewModel.uiFavoritePosts.value is FavoritesPostsUiState.ShowEmptyFavoritesPosts)
        }
    }


}