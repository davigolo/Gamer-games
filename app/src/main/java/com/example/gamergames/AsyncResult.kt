package com.example.gamergames

/**Async result to handle all possible results from HTTP petitions, by doing this the view
 * will know the result*/
data class AsyncResult<T> (
    val state: STATE,
    val data: T? = null,
    val error: String = "",
    val exception: Throwable? = null
    ) {

    enum class STATE {
        SUCCESS,
        ERROR,
        EXCEPTION,
        LOADING
    }

    companion object {
        fun <T> success(data: T): AsyncResult<T> {
            return AsyncResult(STATE.SUCCESS, data)
        }

        fun<T> error(error: String, data: T):AsyncResult<T>{
            return AsyncResult(STATE.ERROR, data, error)
        }

        fun<T> exception(exception: Throwable?, data: T? = null):AsyncResult<T>{
            return AsyncResult(STATE.EXCEPTION, exception = exception)
        }
    }
}


