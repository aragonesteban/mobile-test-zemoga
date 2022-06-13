package com.zemoga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zemoga.data.local.dao.CommentsDao
import com.zemoga.data.local.dao.PostsDao
import com.zemoga.data.local.dao.UsersDao
import com.zemoga.data.local.entities.CommentEntity
import com.zemoga.data.local.entities.PostEntity
import com.zemoga.data.local.entities.UserEntity

@Database(
    entities = [
        PostEntity::class,
        UserEntity::class,
        CommentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ZemogaDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
    abstract fun usersDao(): UsersDao
    abstract fun commentsDao(): CommentsDao
}