package com.zemoga.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.zemoga.data.local.ZemogaDatabase
import com.zemoga.data.local.dao.CommentsDao
import com.zemoga.data.local.dao.PostsDao
import com.zemoga.data.local.dao.UsersDao
import com.zemoga.data.local.entities.CommentEntity
import com.zemoga.data.local.entities.PostEntity
import com.zemoga.data.local.entities.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import kotlin.test.assertEquals

class ZemogaDatabaseTest {

    private lateinit var database: ZemogaDatabase
    private lateinit var postsDao: PostsDao
    private lateinit var usersDao: UsersDao
    private lateinit var commentsDao: CommentsDao

    private val postId = 1
    private val userId = 1
    private val mockPostEntity = PostEntity(postId, userId, "", "", true)
    private val mockListPostItem = listOf(mockPostEntity)
    private val mockUser = UserEntity(userId, "", "", "", "")
    private val mockCommentEntityList = listOf(CommentEntity(1, postId, "", "", ""))

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ZemogaDatabase::class.java).build()
        postsDao = database.postsDao()
        usersDao = database.usersDao()
        commentsDao = database.commentsDao()
    }

    @Test
    fun writeAndUpdateAndReadListPostEntity() = runBlocking {
        postsDao.insertAllPosts(mockListPostItem)
        val postListUpdated = listOf(mockPostEntity.copy(isFavorite = false))
        postsDao.updateAllPosts(postListUpdated)
        val postListInserted = postsDao.getAllPosts()
        assertEquals(postListUpdated, postListInserted)
    }

    @Test
    fun writeAndDeleteAndReadListPostEntity() = runBlocking {
        postsDao.insertAllPosts(mockListPostItem)
        postsDao.deleteAllPosts()
        val postListInserted = postsDao.getAllPosts()
        assertEquals(postListInserted, listOf())
    }

    @Test
    fun writeAndUpdateAndReadPostEntity() = runBlocking {
        postsDao.insertAllPosts(mockListPostItem)
        val postItemUpdated = mockPostEntity.copy(isFavorite = false)
        postsDao.updatePost(postItemUpdated)
        val postItem = postsDao.getPostById(mockPostEntity.id)
        assertEquals(postItem, postItemUpdated)
    }

    @Test
    fun writeAndDeleteAndReadPostEntity() = runBlocking {
        postsDao.insertAllPosts(mockListPostItem)
        postsDao.deletePost(mockPostEntity.id)
        val postListInserted = postsDao.getPostById(mockPostEntity.id)
        assertEquals(postListInserted, null)
    }

    @Test
    fun writeAndReadUserEntity() {
        usersDao.insertUser(mockUser)
        val user = usersDao.getUserById(mockUser.id)
        assertEquals(mockUser, user)
    }

    @Test
    fun writeAndReadListCommentEntity() {
        commentsDao.insertComments(mockCommentEntityList)
        val commentsInserted = commentsDao.getCommentsByPostId(postId)
        assertEquals(commentsInserted, mockCommentEntityList)
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}