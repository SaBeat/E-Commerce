package com.example.e_commerce.domain.usecase.local.product

import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DeleteCollectionProductUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(collection: Collection) = flow {
        emit(Resource.Loading)
        try {
            val deleteProduct = localRepository.deleteProductFromCollection(collection)
            emit(Resource.Success(deleteProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}