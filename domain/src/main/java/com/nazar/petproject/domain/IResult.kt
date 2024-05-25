package com.nazar.petproject.domain


sealed interface IResult<out T> {
    data class Success<T>(val data: T) : IResult<T>
    data class Error(val exception: DomainException? = null, val message: String? = null) : IResult<Nothing>
    data object Loading : IResult<Nothing>
}

suspend inline fun<T> IResult<T>.suspendOnSuccess(crossinline block: suspend IResult.Success<T>.() -> Unit): IResult<T> {
    if (this is IResult.Success) {
        block(this)
    }
    return this
}

inline fun<T> IResult<T>.onSuccess(crossinline block: IResult.Success<T>.() -> Unit): IResult<T> {
    if (this is IResult.Success) {
        block(this)
    }
    return this
}
suspend inline fun<T> IResult<T>.suspendOnError(crossinline block: suspend IResult.Error.() -> Unit): IResult<T> {
    if (this is IResult.Error) {
        block(this)
    }
    return this
}
inline fun<T> IResult<T>.onError(crossinline block: IResult.Error.() -> Unit): IResult<T> {
    if (this is IResult.Error) {
        block(this)
    }
    return this
}