package com.nazar.petproject.domain


sealed interface IResult<out T, out E> {
    data class Success<out T>(val data: T) : IResult<T, Nothing>
    data class Error<out E>(val exception: E) : IResult<Nothing, E>
}

suspend inline fun<T, E> IResult<T, E>.suspendOnSuccess(crossinline block: suspend IResult.Success<T>.() -> Unit): IResult<T, E> {
    if (this is IResult.Success) {
        block(this)
    }
    return this
}

inline fun<T, E> IResult<T, E>.onSuccess(crossinline block: IResult.Success<T>.() -> Unit): IResult<T, E> {
    if (this is IResult.Success) {
        block(this)
    }
    return this
}
suspend inline fun<T, E> IResult<T, E>.suspendOnError(crossinline block: suspend IResult.Error<E>.() -> Unit): IResult<T, E> {
    if (this is IResult.Error) {
        block(this)
    }
    return this
}
inline fun<T, E> IResult<T, E>.onError(crossinline block: IResult.Error<E>.() -> Unit): IResult<T, E> {
    if (this is IResult.Error) {
        block(this)
    }
    return this
}