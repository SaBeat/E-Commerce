package com.example.e_commerce.domain.usecase.local.product

import com.example.e_commerce.common.Resource
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetPurchasedItemsCountUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(userId: String) = channelFlow {
        send(Resource.Loading)
        try {
            val purchasedProducts = localRepository.getPurchasedProduct(userId)
            CoroutineScope(currentCoroutineContext()).launch {
                purchasedProducts.collect { purchasedList ->
                    send(Resource.Success(purchasedList.size))
                }
            }

        } catch (e: Exception) {
            send(Resource.Error(e.localizedMessage))
            throw Exception(e.localizedMessage)
        }
    }.flowOn(ioDispatcher)
}