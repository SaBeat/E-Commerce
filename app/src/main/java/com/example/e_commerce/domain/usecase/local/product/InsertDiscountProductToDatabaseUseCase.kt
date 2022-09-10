package com.example.e_commerce.domain.usecase.local.product

import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.DiscountProduct
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.data.entities.product.ProductsItem
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertDiscountProductToDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(productsItem: MutableList<DiscountProduct>) = flow {
        emit(Resource.Loading)
        try {
            val discountProduct = localRepository.insertDiscountProductToDatabase(productsItem)
            emit(Resource.Success(discountProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}