package com.zemoga.shared.navigation

import android.content.Context
import android.content.Intent
import com.zemoga.shared.POST_ID_KEY
import com.zemoga.shared.USER_ID_KEY

fun Intent.goToPostDetail(
    context: Context,
    postId: Int,
    userId: Int
): Intent {
    action = "action.PostDetailActivity.open"
    setPackage(context.packageName)
    putExtra(POST_ID_KEY, postId)
    putExtra(USER_ID_KEY, userId)
    return this
}