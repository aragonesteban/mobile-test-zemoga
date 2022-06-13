package com.zemoga.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.usecases.comments.CommentsUseCase
import com.zemoga.domain.usecases.posts.PostsUseCase
import com.zemoga.domain.usecases.users.UsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postsUseCase: PostsUseCase,
    private val usersUseCase: UsersUseCase,
    private val commentsUseCase: CommentsUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<PostDetailUiState>(PostDetailUiState.LoadingPostDetail)
    val viewState: StateFlow<PostDetailUiState> = _viewState

    fun getPostById(postId: Int) {
        viewModelScope.launch {
            _viewState.value = PostDetailUiState.LoadingPostDetail
            _viewState.value = when (val result = postsUseCase.getPostById(postId)) {
                is ZemogaResult.Success -> PostDetailUiState.ShowPostDetail(result.data)
                is ZemogaResult.Error -> PostDetailUiState.Error
            }
        }
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            _viewState.value = PostDetailUiState.LoadingUserInfo
            _viewState.value = when (val result = usersUseCase.getUserById(userId)) {
                is ZemogaResult.Success -> PostDetailUiState.ShowUserInfo(result.data)
                is ZemogaResult.Error -> PostDetailUiState.Error
            }
        }
    }

    fun getCommentsByPostId(postId: Int) {
        viewModelScope.launch {
            _viewState.value = PostDetailUiState.LoadingComments
            _viewState.value = when (val result = commentsUseCase.getCommentsByPostId(postId)) {
                is ZemogaResult.Success -> PostDetailUiState.ShowPostComments(result.data)
                is ZemogaResult.Error -> PostDetailUiState.Error
            }
        }
    }

}