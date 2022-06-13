package com.zemoga.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zemoga.domain.ZemogaResult
import com.zemoga.domain.model.PostItem
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

    private val _uiState =
        MutableStateFlow<PostDetailUiState>(PostDetailUiState.LoadingPostDetail)
    val uiState: StateFlow<PostDetailUiState> = _uiState

    private var _postDetail: PostItem? = null

    fun getPostById(postId: Int) {
        viewModelScope.launch {
            _uiState.value = PostDetailUiState.LoadingPostDetail
            _uiState.value = when (val result = postsUseCase.getPostById(postId)) {
                is ZemogaResult.Success -> {
                    _postDetail = result.data
                    PostDetailUiState.ShowPostDetail(result.data)
                }
                is ZemogaResult.Error -> PostDetailUiState.Error
            }
        }
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            _uiState.value = PostDetailUiState.LoadingUserInfo
            _uiState.value = when (val result = usersUseCase.getUserById(userId)) {
                is ZemogaResult.Success -> PostDetailUiState.ShowUserInfo(result.data)
                is ZemogaResult.Error -> PostDetailUiState.Error
            }
        }
    }

    fun getCommentsByPostId(postId: Int) {
        viewModelScope.launch {
            _uiState.value = PostDetailUiState.LoadingComments
            _uiState.value = when (val result = commentsUseCase.getCommentsByPostId(postId)) {
                is ZemogaResult.Success -> PostDetailUiState.ShowPostComments(result.data)
                is ZemogaResult.Error -> PostDetailUiState.Error
            }
        }
    }

    fun updatePostFavorite() {
        viewModelScope.launch {
            _postDetail?.let { post ->
                post.isFavorite = post.isFavorite.not()
                postsUseCase.updatePost(post)
                _uiState.value = PostDetailUiState.ToggleFavoriteStar(post.isFavorite)
            }
        }
    }

    fun deletePost() {
        viewModelScope.launch {
            _postDetail?.let { post ->
                postsUseCase.deletePost(post.id)
                _uiState.value = PostDetailUiState.ShowMessagePostDeleted
            }
        }
    }

}