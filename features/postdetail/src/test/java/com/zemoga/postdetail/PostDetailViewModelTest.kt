package com.zemoga.postdetail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.model.User
import com.zemoga.domain.usecases.comments.CommentsUseCase
import com.zemoga.domain.usecases.posts.PostsUseCase
import com.zemoga.domain.usecases.users.UsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PostDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()

    private val postId = 1
    private val userId = 1
    private val mockPostItem = PostItem(
        userId = userId,
        id = postId,
        title = "test",
        body = "test",
        isFavorite = true,
    )
    private val mockUser = User(
        id = userId,
        name = "test",
        email = "test",
        phone = "test",
        website = "test",
    )
    private val mockCommentsList = listOf(
        CommentItem(
            id = 1,
            postId = postId,
            name = "test",
            email = "test",
            body = "test"
        )
    )


    private val mockPostsUseCase = mock<PostsUseCase> {
        onBlocking { getPostById(postId) } doReturn ZemogaResult.Success(mockPostItem)
        onBlocking { updatePost(mockPostItem) } doReturn Unit
        onBlocking { deletePost(postId) } doReturn Unit
    }

    private val mockUsersUseCase = mock<UsersUseCase> {
        onBlocking { getUserById(postId) } doReturn ZemogaResult.Success(mockUser)
    }

    private val mockCommentsUseCase = mock<CommentsUseCase> {
        onBlocking { getCommentsByPostId(postId) } doReturn ZemogaResult.Success(mockCommentsList)
    }

    private lateinit var viewModel: PostDetailViewModel

    @Before
    fun setUp() {
        viewModel = PostDetailViewModel(mockPostsUseCase, mockUsersUseCase, mockCommentsUseCase)
    }

    @Test
    fun `getPostById() verify that getPostById() is calling from PostsUseCase`() {
        runTest {
            viewModel.getPostById(postId)
            verify(mockPostsUseCase).getPostById(postId)
        }
    }

    @Test
    fun `getPostById() when use case returns ZemogaResult Success then state emit ShowPostDetail`() {
        runTest {
            viewModel.getPostById(postId)
            assertTrue(viewModel.uiState.value is PostDetailUiState.ShowPostDetail)
        }
    }

    @Test
    fun `getUserById() verify that getUserById() is calling from UsersUseCase`() {
        runTest {
            viewModel.getUserById(userId)
            verify(mockUsersUseCase).getUserById(userId)
        }
    }

    @Test
    fun `getUserById() when use case returns ZemogaResult Success then state emit ShowUserInfo`() {
        runTest {
            viewModel.getUserById(userId)
            assertTrue(viewModel.uiState.value is PostDetailUiState.ShowUserInfo)
        }
    }

    @Test
    fun `getCommentsByPostId() verify that getCommentsByPostId() is calling from CommentsUseCase`() {
        runTest {
            viewModel.getCommentsByPostId(postId)
            verify(mockCommentsUseCase).getCommentsByPostId(postId)
        }
    }

    @Test
    fun `getCommentsByPostId() when use case returns ZemogaResult Success then state emit ShowPostComments`() {
        runTest {
            viewModel.getCommentsByPostId(postId)
            assertTrue(viewModel.uiState.value is PostDetailUiState.ShowPostComments)
        }
    }

    @Test
    fun `updatePostFavorite() verify that updatePost() is calling from PostsUseCase`() {
        runTest {
            viewModel.getPostById(postId)
            viewModel.updatePostFavorite()
            verify(mockPostsUseCase).updatePost(mockPostItem)
        }
    }

    @Test
    fun `updatePostFavorite() when use case executes updatePost() then state emit ToggleFavoriteStar`() {
        runTest {
            viewModel.getPostById(postId)
            viewModel.updatePostFavorite()
            assertTrue(viewModel.uiState.value is PostDetailUiState.ToggleFavoriteStar)
        }
    }

    @Test
    fun `deletePost() verify that deletePost() is calling from PostsUseCase`() {
        runTest {
            viewModel.getPostById(postId)
            viewModel.deletePost()
            verify(mockPostsUseCase).deletePost(postId)
        }
    }

    @Test
    fun `deletePost() when use case executes deletePost() then state emit ShowMessagePostDeleted`() {
        runTest {
            viewModel.getPostById(postId)
            viewModel.deletePost()
            assertTrue(viewModel.uiState.value is PostDetailUiState.ShowMessagePostDeleted)
        }
    }

}