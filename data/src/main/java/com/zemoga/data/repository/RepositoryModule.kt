package com.zemoga.data.repository

import com.zemoga.data.remote.REMOTE_COMMENTS
import com.zemoga.data.remote.REMOTE_POSTS
import com.zemoga.data.remote.REMOTE_USERS
import com.zemoga.domain.repository.CommentsRepository
import com.zemoga.domain.repository.PostsRepository
import com.zemoga.domain.repository.UsersRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val POSTS_REPOSITORY = "PostsRepository"
const val USERS_REPOSITORY = "UsersRepository"
const val COMMENTS_REPOSITORY = "CommentsRepository"

val repositoryModule = module {

    factory<PostsRepository>(named(POSTS_REPOSITORY)) {
        PostsRepositoryImpl(get(named(REMOTE_POSTS)))
    }

    factory<UsersRepository>(named(USERS_REPOSITORY)) {
        UsersRepositoryImpl(get(named(REMOTE_USERS)))
    }

    factory<CommentsRepository>(named(COMMENTS_REPOSITORY)) {
        CommentsRepositoryImpl(get(named(REMOTE_COMMENTS)))
    }

}