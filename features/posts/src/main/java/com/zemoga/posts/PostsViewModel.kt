package com.zemoga.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.usecases.posts.PostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostsViewModel(private val postsUseCase: PostsUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val viewState: StateFlow<PostsUiState> = _viewState

    fun getAllPosts() {
        viewModelScope.launch {
            _viewState.value = when (val result = postsUseCase.getPostsList()) {
                is ZemogaResult.Success -> PostsUiState.ShowAllPosts(result.data)
                is ZemogaResult.Error -> PostsUiState.Error
            }
        }
    }

    fun getFavoritesPosts() {

    }

}