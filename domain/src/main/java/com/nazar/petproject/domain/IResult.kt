package com.nazar.petproject.domain


sealed interface IResult<out T, out E> {
    data class Success<out T>(val data: T) : IResult<T, Nothing>
    data class Error<out E>(val exception: E) : IResult<Nothing, E>
}

suspend inline fun<T> IResult<T, Nothing>.suspendOnSuccess(crossinline block: suspend IResult.Success<T>.() -> Unit): IResult<T, Nothing> {
    if (this is IResult.Success) {
        block(this)
    }
    return this
}

inline fun<T> IResult<T, Nothing>.onSuccess(crossinline block: IResult.Success<T>.() -> Unit): IResult<T, Nothing> {
    if (this is IResult.Success) {
        block(this)
    }
    return this
}
suspend inline fun<E> IResult<Nothing, E>.suspendOnError(crossinline block: suspend IResult.Error<E>.() -> Unit): IResult<Nothing, E> {
    if (this is IResult.Error) {
        block(this)
    }
    return this
}
inline fun<E> IResult<Nothing, E>.onError(crossinline block: IResult.Error<E>.() -> Unit): IResult<Nothing, E> {
    if (this is IResult.Error) {
        block(this)
    }
    return this
}