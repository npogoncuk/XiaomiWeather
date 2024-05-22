package com.nazar.petproject.domain

sealed interface Result<T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error<T>(val exception: Exception, val message: String?) : Result<T>
    data object Loading : Result<Nothing>
}