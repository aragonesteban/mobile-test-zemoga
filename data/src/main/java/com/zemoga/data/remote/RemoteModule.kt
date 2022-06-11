package com.zemoga.data.remote

import com.zemoga.data.remote.api.buildRetrofit
import com.zemoga.data.remote.api.comments.CommentsApi
import com.zemoga.data.remote.api.posts.PostsApi
import com.zemoga.data.remote.api.users.UsersApi
import com.zemoga.data.remote.comments.RemoteCommentsImpl
import com.zemoga.data.remote.posts.RemotePostsImpl
import com.zemoga.data.remote.users.RemoteUsersImpl
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

const val ZEMOGA_RETROFIT = "ZemogaRetrofit"

const val POSTS_API = "PostsApi"
const val USERS_API = "UsersApi"
const val COMMENTS_API = "CommentsApi"

const val REMOTE_POSTS = "RemotePosts"
const val REMOTE_USERS = "RemoteUsers"
const val REMOTE_COMMENTS = "RemoteComments"

@ExperimentalSerializationApi
val remoteModule = module {
    single(named(ZEMOGA_RETROFIT)) {
        buildRetrofit(BASE_URL)
    }

    single(named(POSTS_API)) {
        PostsApi.buildPostsApi(retrofit = get(named(ZEMOGA_RETROFIT)))
    }

    single(named(USERS_API)) {
        UsersApi.buildUsersApi(retrofit = get(named(ZEMOGA_RETROFIT)))
    }

    single(named(COMMENTS_API)) {
        CommentsApi.buildCommentsApi(retrofit = get(named(ZEMOGA_RETROFIT)))
    }

    factory(named(REMOTE_POSTS)) {
        RemotePostsImpl(postsApi = get(named(POSTS_API)))
    }

    factory(named(REMOTE_USERS)) {
        RemoteUsersImpl(usersApi = get(named(USERS_API)))
    }

    factory(named(REMOTE_COMMENTS)) {
        RemoteCommentsImpl(commentsApi = get(named(COMMENTS_API)))
    }
}