package com.zemoga.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.data.local.users.LocalUsers
import com.zemoga.data.remote.users.RemoteUsers
import com.zemoga.data.repository.UsersRepositoryImpl
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.User
import com.zemoga.domain.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UsersRepositoryTest {

    private val userId = 1
    private val mockUser = User(
        id = userId,
        email = "test",
        phone = "test",
        name = "test",
        website = "test"
    )

    private val mockRemoteUsers = mock<RemoteUsers> {
        onBlocking { getUserById(userId) } doReturn ZemogaResult.Success(mockUser)
    }

    private val mockLocalUsers = mock<LocalUsers> {
        onBlocking { getUserById(userId) } doReturn null
    }

    private lateinit var repository: UsersRepository

    @Before
    fun setUp() {
        repository = UsersRepositoryImpl(mockLocalUsers, mockRemoteUsers)
    }

    @Test
    fun `getUserById() verify that getUserById() is calling from RemoteUsers`() {
        runTest {
            repository.getUserById(userId)
            verify(mockRemoteUsers).getUserById(userId)
        }
    }

    @Test
    fun `getUserById() verify that getUserById() is calling from LocalUsers`() {
        runTest {
            repository.getUserById(userId)
            verify(mockLocalUsers).getUserById(userId)
        }
    }

    @Test
    fun `getUserById() when remote returns Zemoga Success then returns Success`() {
        runTest {
            val result = repository.getUserById(userId)
            assertTrue(result is ZemogaResult.Success)
        }
    }

}