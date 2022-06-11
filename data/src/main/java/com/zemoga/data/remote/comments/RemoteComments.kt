package com.zemoga.data.remote.comments

import com.zemoga.domain.ZemogaResult

interface RemoteComments {
    suspend fun getCommentsByPostId(postId: Int): ZemogaResult<List<Any>>
}