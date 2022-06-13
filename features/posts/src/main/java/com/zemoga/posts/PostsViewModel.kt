package com.zemoga.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.usecases.posts.PostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostsViewModel(private val postsUseCase: PostsUseCase) : ViewModel() {

    private val _uiStateAllPosts = MutableStateFlow<AllPostsUiState>(AllPostsUiState.Loading)
    val uiStateAllPosts: StateFlow<AllPostsUiState> = _uiStateAllPosts

    private val _uiStateFavoritesPosts =
        MutableStateFlow<FavoritesPostsUiState>(FavoritesPostsUiState.Loading)
    val uiFavoritePosts: StateFlow<FavoritesPostsUiState> = _uiStateFavoritesPosts

    private var _postsDeleted = false

    fun getAllPosts(loadPostsFromApi: Boolean = false) {
        if (loadPostsFromApi) _postsDeleted = false
        if (_postsDeleted.not()) {
            viewModelScope.launch {
                _uiStateAllPosts.value = AllPostsUiState.Loading
                _uiStateAllPosts.value =
                    when (val result = postsUseCase.getPostsList(loadPostsFromApi)) {
                        is ZemogaResult.Success -> {
                            getFavoritesPosts(result.data)
                            AllPostsUiState.ShowAllPosts(result.data)
                        }
                        is ZemogaResult.Error -> AllPostsUiState.Error
                    }
            }
        }
    }

    private fun getFavoritesPosts(postsList: List<PostItem>) {
        val favoritesPostsList = postsUseCase.filterFavoritePostsList(postsList)
        _uiStateFavoritesPosts.value = if (favoritesPostsList.isNotEmpty()) {
            FavoritesPostsUiState.ShowFavoritesPosts(favoritesPostsList)
        } else {
            FavoritesPostsUiState.ShowEmptyFavoritesPosts
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch {
            _postsDeleted = true
            postsUseCase.deleteAllPost()
            _uiStateAllPosts.value = AllPostsUiState.ShowEmptyPosts
            _uiStateFavoritesPosts.value = FavoritesPostsUiState.ShowEmptyFavoritesPosts
        }
    }

}