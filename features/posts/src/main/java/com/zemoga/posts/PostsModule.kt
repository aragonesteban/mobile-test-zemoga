package com.zemoga.posts

import com.zemoga.domain.usecases.POSTS_USE_CASE
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val POSTS_VIEW_MODEL = "PostsViewModel"

val postsModule = module {
    viewModel(named(POSTS_VIEW_MODEL)) {
        PostsViewModel(postsUseCase = get(named(POSTS_USE_CASE)))
    }
}