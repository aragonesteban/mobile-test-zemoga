package com.zemoga.posts

import com.zemoga.domain.model.PostItem

sealed interface FavoritesPostsUiState {
    object Loading : FavoritesPostsUiState
    data class ShowFavoritesPosts(val data: List<PostItem>) : FavoritesPostsUiState
    object ShowEmptyFavoritesPosts : FavoritesPostsUiState
    object Error : FavoritesPostsUiState
}