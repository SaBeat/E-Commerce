package com.example.e_commerce.domain.usecase.remote

import com.example.e_commerce.common.Resource
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddProductToBagUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(
        user: String,
        title: String,
        price: Double,
        description: String,
        category: String,
        image: String,
        rate: Double,
        count: Int,
        sale_state: Int
    ) = flow {
        emit(Resource.Loading)
        try {
            val addProduct = remoteRepository.addProductToBag(
                user,
                title,
                price,
                description,
                category,
                image,
                rate,
                count,
                sale_state
            )
            emit(Resource.Success(addProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}