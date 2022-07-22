package com.example.capstoneproject.domain.usecase.local.product

import com.example.e_commerce.common.Resource
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetFavoritesProductsFromDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(userId: String) = flow {
        emit(Resource.Loading)
        try {
            val favorites = localRepository.getFavoritesProduct(userId)
            emit(Resource.Success(favorites))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}