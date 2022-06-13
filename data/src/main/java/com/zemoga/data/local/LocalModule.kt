package com.zemoga.data.local

import androidx.room.Room
import com.zemoga.data.local.comments.LocalComments
import com.zemoga.data.local.comments.LocalCommentsImpl
import com.zemoga.data.local.posts.LocalPosts
import com.zemoga.data.local.posts.LocalPostsImpl
import com.zemoga.data.local.users.LocalUsers
import com.zemoga.data.local.users.LocalUsersImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val NAME_DATABASE = "zemoga_database"

const val POSTS_DAO = "PostsDao"
const val USERS_DAO = "UsersDao"
const val COMMENTS_DAO = "CommentsDao"

const val LOCAL_POSTS = "LocalPosts"
const val LOCAL_USERS = "LocalUsers"
const val LOCAL_COMMENTS = "LocalComments"

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

    factory(named(USERS_DAO)) { get<ZemogaDatabase>().usersDao() }

    factory(named(COMMENTS_DAO)) { get<ZemogaDatabase>().commentsDao() }

    factory<LocalPosts>(named(LOCAL_POSTS)) {
        LocalPostsImpl(get(named(POSTS_DAO)))
    }

    factory<LocalUsers>(named(LOCAL_USERS)) {
        LocalUsersImpl(get(named(USERS_DAO)))
    }

    factory<LocalComments>(named(LOCAL_COMMENTS)) {
        LocalCommentsImpl(get(named(COMMENTS_DAO)))
    }

}