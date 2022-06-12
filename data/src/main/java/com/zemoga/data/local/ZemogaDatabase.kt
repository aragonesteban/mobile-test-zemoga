package com.zemoga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zemoga.data.local.dao.PostsDao
import com.zemoga.data.local.entities.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1
)
abstract class ZemogaDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}