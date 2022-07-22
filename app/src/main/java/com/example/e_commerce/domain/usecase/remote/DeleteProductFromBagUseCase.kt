package com.example.e_commerce.domain.usecase.remote

import com.example.e_commerce.common.Resource
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteProductFromBagUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(id:Int) = flow {
        emit(Resource.Loading)
        try {
            val deleteProductFromBag = remoteRepository.deleteFromBag(id)
            emit(Resource.Success(deleteProductFromBag))
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }.flowOn(ioDispatcher)

}