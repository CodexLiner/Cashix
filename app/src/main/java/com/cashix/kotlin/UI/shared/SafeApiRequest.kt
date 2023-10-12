package com.cashix.kotlin.UI.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

object SafeApiRequest {
    fun safe(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch {
        work()
    }
    public suspend fun <T : Any> apiRequest(call: suspend () -> retrofit2.Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw IOException(response.errorBody()?.string() ?: "Unknown Error")
        }
    }
}