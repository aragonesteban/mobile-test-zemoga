package com.zemoga.data.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.zemoga.data.BuildConfig
import com.zemoga.domain.ZemogaResult
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

fun logging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

@ExperimentalSerializationApi
fun buildRetrofit(baseUrl: String): Retrofit {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideHttpclient())
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
}

fun provideHttpclient(): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    if (BuildConfig.DEBUG) {
        builder.addInterceptor(OkHttpProfilerInterceptor())
    }
    return builder
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(logging())
        .build()
}

internal inline fun <T : Any> executeRetrofitRequest(block: () -> Response<T>): ZemogaResult<T> {
    return try {
        val request = block.invoke()
        return if (request.isSuccessful && request.body() != null) {
            val body = request.body()
            if (body != null) {
                ZemogaResult.Success(body)
            } else {
                ZemogaResult.Error(request.message(), request.code())
            }
        } else {
            val errorBody = request.errorBody()
            val errorText = errorBody?.string() ?: "Error body null"
            ZemogaResult.Error(errorText, request.code())
        }
    } catch (exception: UnknownHostException) {
        ZemogaResult.Error(exception.toString())
    }
}

fun <Api : Any, Data : Any> handleResultRetrofit(
    result: ZemogaResult<Api>,
    onSuccess: (Api) -> Data
): ZemogaResult<Data> {
    return when (result) {
        is ZemogaResult.Success -> ZemogaResult.Success(onSuccess.invoke(result.data))
        is ZemogaResult.Error -> result
    }
}