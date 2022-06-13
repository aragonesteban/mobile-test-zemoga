package com.zemoga.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.zemoga.domain.model.User
import com.zemoga.domain.repository.UsersRepository
import com.zemoga.domain.usecases.users.UsersUseCase
import com.zemoga.domain.usecases.users.UsersUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UsersUseCaseTest {

    private val userId = 1
    private val mockUser = User(
        id = userId,
        name = "test",
        email = "test",
        phone = "test",
        website = "test",
    )

    private val mockUserRepository = mock<UsersRepository> {
        onBlocking { getUserById(userId) } doReturn ZemogaResult.Success(mockUser)
    }

    private lateinit var useCase: UsersUseCase

    @Before
    fun setUp() {
        useCase = UsersUseCaseImpl(mockUserRepository)
    }

    @Test
    fun `getUserById() verify that getUserById() is calling from UsersRepository`() {
        runTest {
            useCase.getUserById(userId)
            verify(mockUserRepository).getUserById(userId)
        }
    }

    @Test
    fun `getUserById() when repository returns ZemogaResult Success then use case returns a ZemogaResult Success`() {
        runTest {
            val result = useCase.getUserById(userId)
            assertTrue(result is ZemogaResult.Success)
        }
    }

}