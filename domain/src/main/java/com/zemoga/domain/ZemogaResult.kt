package com.zemoga.domain

sealed interface ZemogaResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ZemogaResult<T>
    data class Error(val message: String, val code: Int? = null) : ZemogaResult<Nothing>
}