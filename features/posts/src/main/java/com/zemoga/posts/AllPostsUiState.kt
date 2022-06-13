package com.zemoga.posts

import com.zemoga.domain.model.PostItem

sealed interface AllPostsUiState {
    object Loading : AllPostsUiState
    data class ShowAllPosts(val data: List<PostItem>) : AllPostsUiState
    object ShowEmptyPosts : AllPostsUiState
    object Error : AllPostsUiState
}