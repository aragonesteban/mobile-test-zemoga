package com.zemoga.data.remote.api.users

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val USERS_SLUG = "users"
private const val USER_ID = "userId"

interface UsersApi {

    @GET("$USERS_SLUG/{$USER_ID}")
    fun getUserById(@Path(USER_ID) userId: Int): Response<UsersResponse>

    companion object {
        fun buildUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
    }

}