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

    private val _uiStateAllPosts = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val uiStateAllPosts: StateFlow<PostsUiState> = _uiStateAllPosts

    private val _uiStateFavoritesPosts = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val uiFavoritePosts: StateFlow<PostsUiState> = _uiStateFavoritesPosts

    private var _postsDeleted = false

    fun getAllPosts(loadPostsFromApi: Boolean = false) {
        if (loadPostsFromApi) _postsDeleted = false
        if (_postsDeleted.not()) {
            viewModelScope.launch {
                _uiStateAllPosts.value = PostsUiState.Loading
                _uiStateAllPosts.value =
                    when (val result = postsUseCase.getPostsList(loadPostsFromApi)) {
                        is ZemogaResult.Success -> {
                            getFavoritesPosts(result.data)
                            PostsUiState.ShowAllPosts(result.data)
                        }
                        is ZemogaResult.Error -> PostsUiState.Error
                    }
            }
        }
    }

    private fun getFavoritesPosts(postsList: List<PostItem>) {
        val favoritesPostsList = postsUseCase.filterFavoritePostsList(postsList)
        _uiStateFavoritesPosts.value = if (favoritesPostsList.isNotEmpty()) {
            PostsUiState.ShowFavoritesPosts(favoritesPostsList)
        } else {
            PostsUiState.ShowEmptyFavoritesPosts
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch {
            _postsDeleted = true
            postsUseCase.deleteAllPost()
            _uiStateAllPosts.value = PostsUiState.ShowEmptyPosts
            _uiStateFavoritesPosts.value = PostsUiState.ShowEmptyFavoritesPosts
        }
    }

}