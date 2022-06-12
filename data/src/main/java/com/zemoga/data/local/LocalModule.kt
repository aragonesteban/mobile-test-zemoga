package com.zemoga.data.local

import androidx.room.Room
import com.zemoga.data.local.posts.LocalPosts
import com.zemoga.data.local.posts.LocalPostsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val NAME_DATABASE = "zemoga_database"

const val POSTS_DAO = "PostsDao"

const val LOCAL_POSTS = "LocalPosts"

val cacheLocalModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ZemogaDatabase::class.java,
            NAME_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }

    factory(named(POSTS_DAO)) { get<ZemogaDatabase>().postsDao() }

    factory<LocalPosts>(named(LOCAL_POSTS)) {
        LocalPostsImpl(get(named(POSTS_DAO)))
    }
}