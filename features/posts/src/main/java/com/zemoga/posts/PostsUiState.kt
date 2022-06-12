package com.zemoga.posts

import com.zemoga.domain.model.PostItem

sealed interface PostsUiState {
    object Loading : PostsUiState
    data class ShowAllPosts(val data: List<PostItem>) : PostsUiState
    data class ShowFavoritesPosts(val data: List<PostItem>) : PostsUiState
    object Error : PostsUiState
}
