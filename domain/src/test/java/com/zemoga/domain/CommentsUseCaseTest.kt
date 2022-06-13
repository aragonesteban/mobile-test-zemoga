package com.zemoga.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.repository.CommentsRepository
import com.zemoga.domain.usecases.comments.CommentsUseCase
import com.zemoga.domain.usecases.comments.CommentsUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsUseCaseTest {

    private val postId = 1
    private val mockCommentsList = listOf(
        CommentItem(
            id = 1,
            postId = postId,
            name = "test",
            email = "test",
            body = "test"
        )
    )


    private val mockCommentsRepository = mock<CommentsRepository> {
        onBlocking { getCommentsByPostId(postId) } doReturn ZemogaResult.Success(mockCommentsList)
    }

    private lateinit var useCase: CommentsUseCase

    @Before
    fun setUp() {
        useCase = CommentsUseCaseImpl(mockCommentsRepository)
    }

    @Test
    fun `getCommentsByPostId() verify that getCommentsByPostId() is calling from CommentsRepository`() {
        runTest {
            useCase.getCommentsByPostId(postId)
            verify(mockCommentsRepository).getCommentsByPostId(postId)
        }
    }

    @Test
    fun `getCommentsByPostId() when repository returns ZemogaResult Success then use case returns a ZemogaResult Success`() {
        runTest {
            val result = useCase.getCommentsByPostId(postId)
            assertTrue(result is ZemogaResult.Success)
        }
    }

}