package com.zemoga.postdetail

import com.zemoga.domain.usecases.COMMENTS_USE_CASE
import com.zemoga.domain.usecases.POSTS_USE_CASE
import com.zemoga.domain.usecases.USERS_USE_CASE
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val POST_DETAIL_VIEW_MODEL = "PostDetailViewModel"

val postDetailModule = module {
    viewModel(named(POST_DETAIL_VIEW_MODEL)) {
        PostDetailViewModel(
            get(named(POSTS_USE_CASE)),
            get(named(USERS_USE_CASE)),
            get(named(COMMENTS_USE_CASE))
        )
    }
}