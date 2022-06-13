package com.zemoga.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.data.local.comments.LocalComments
import com.zemoga.data.remote.comments.RemoteComments
import com.zemoga.data.repository.CommentsRepositoryImpl
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.repository.CommentsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsRepositoryTest {

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

    private val mockRemoteComments = mock<RemoteComments> {
        onBlocking { getCommentsByPostId(postId) } doReturn ZemogaResult.Success(mockCommentsList)
    }

    private val mockLocalComments = mock<LocalComments> {
        onBlocking { getCommentsByPostId(postId) } doReturn listOf()
    }

    private lateinit var repository: CommentsRepository

    @Before
    fun setUp() {
        repository = CommentsRepositoryImpl(mockLocalComments, mockRemoteComments)
    }

    @Test
    fun `getCommentsByPostId() verify that getCommentsByPostId() is calling from RemoteComments`() {
        runTest {
            repository.getCommentsByPostId(postId)
            verify(mockRemoteComments).getCommentsByPostId(postId)
        }
    }

    @Test
    fun `getCommentsByPostId() verify that getCommentsByPostId() is calling from LocalComments`() {
        runTest {
            repository.getCommentsByPostId(postId)
            verify(mockLocalComments).getCommentsByPostId(postId)
        }
    }

    @Test
    fun `getCommentsByPostId() when remote returns Zemoga Success then returns Success`() {
        runTest {
            val result = repository.getCommentsByPostId(postId)
            assertTrue(result is ZemogaResult.Success)
        }
    }

}