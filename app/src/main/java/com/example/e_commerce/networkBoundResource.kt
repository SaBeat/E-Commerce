package com.example.e_commerce

import com.example.e_commerce.common.Resource
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading)

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (e:Exception) {
            throw Exception("Network Exception")
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}

