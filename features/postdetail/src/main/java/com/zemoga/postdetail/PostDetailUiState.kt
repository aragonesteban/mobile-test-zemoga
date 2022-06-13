package com.zemoga.postdetail

import com.zemoga.domain.model.CommentItem
import com.zemoga.domain.model.PostItem
import com.zemoga.domain.model.User

sealed interface PostDetailUiState {
    object LoadingPostDetail : PostDetailUiState
    data class ShowPostDetail(val data: PostItem) : PostDetailUiState
    object LoadingUserInfo : PostDetailUiState
    data class ShowUserInfo(val data: User) : PostDetailUiState
    object LoadingComments : PostDetailUiState
    data class ShowPostComments(val data: List<CommentItem>) : PostDetailUiState
    object Error : PostDetailUiState
}