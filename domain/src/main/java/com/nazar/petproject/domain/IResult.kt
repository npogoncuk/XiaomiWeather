package com.nazar.petproject.domain


sealed interface IResult<out T> {
    data class Success<T>(val data: T) : IResult<T>
    data class Error(val exception: DomainException? = null, val message: String? = null) : IResult<Nothing>
    data object Loading : IResult<Nothing>
}