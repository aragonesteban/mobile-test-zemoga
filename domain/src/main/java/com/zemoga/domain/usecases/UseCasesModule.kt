package com.zemoga.domain.usecases

import com.zemoga.domain.usecases.comments.CommentsUseCase
import com.zemoga.domain.usecases.comments.CommentsUseCaseImpl
import com.zemoga.domain.usecases.posts.PostsUseCase
import com.zemoga.domain.usecases.posts.PostsUseCaseImpl
import com.zemoga.domain.usecases.users.UsersUseCase
import com.zemoga.domain.usecases.users.UsersUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val POSTS_USE_CASE = "PostsUseCase"
const val USERS_USE_CASE = "UsersUseCase"
const val COMMENTS_USE_CASE = "CommentsUseCase"

const val POSTS_REPOSITORY = "PostsRepository"
const val USERS_REPOSITORY = "UsersRepository"
const val COMMENTS_REPOSITORY = "CommentsRepository"

val useCasesModule = module {

    factory<UsersUseCase>(named(USERS_USE_CASE)) {
        UsersUseCaseImpl(get(named(USERS_REPOSITORY)))
    }

    factory<PostsUseCase>(named(POSTS_USE_CASE)) {
        PostsUseCaseImpl(get(named(POSTS_REPOSITORY)))
    }

    factory<CommentsUseCase>(named(COMMENTS_USE_CASE)) {
        CommentsUseCaseImpl(get(named(COMMENTS_REPOSITORY)))
    }

}