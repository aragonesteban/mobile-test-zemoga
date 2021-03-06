package com.zemoga.data.repository

import com.zemoga.data.local.LOCAL_COMMENTS
import com.zemoga.data.local.LOCAL_POSTS
import com.zemoga.data.local.LOCAL_USERS
import com.zemoga.data.local.POSTS_DAO
import com.zemoga.data.remote.REMOTE_COMMENTS
import com.zemoga.data.remote.REMOTE_POSTS
import com.zemoga.data.remote.REMOTE_USERS
import com.zemoga.domain.repository.CommentsRepository
import com.zemoga.domain.repository.PostsRepository
import com.zemoga.domain.repository.UsersRepository
import com.zemoga.domain.usecases.COMMENTS_REPOSITORY
import com.zemoga.domain.usecases.POSTS_REPOSITORY
import com.zemoga.domain.usecases.USERS_REPOSITORY
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    factory<PostsRepository>(named(POSTS_REPOSITORY)) {
        PostsRepositoryImpl(
            get(named(LOCAL_POSTS)),
            get(named(REMOTE_POSTS))
        )
    }

    factory<UsersRepository>(named(USERS_REPOSITORY)) {
        UsersRepositoryImpl(
            get(named(LOCAL_USERS)),
            get(named(REMOTE_USERS))
        )
    }

    factory<CommentsRepository>(named(COMMENTS_REPOSITORY)) {
        CommentsRepositoryImpl(
            get(named(LOCAL_COMMENTS)),
            get(named(REMOTE_COMMENTS))
        )
    }

}