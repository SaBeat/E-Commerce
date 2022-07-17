package com.example.e_commerce.data.repository

import com.example.e_commerce.data.entities.product.ProductsItem
import com.example.e_commerce.data.model.CRUDResponse
import com.example.e_commerce.data.remote.ProductApi
import com.example.e_commerce.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val productApi: ProductApi) :
    RemoteRepository {
    override suspend fun addProductToBag(
        user: String,
        title: String,
        price: Double,
        description: String,
        category: String,
        image: String,
        rate: Double,
        count: Int,
        sale_state: Int
    ): CRUDResponse = productApi.addProductToBag(
        user, title, price, description, category, image, rate, count, sale_state
    )

    override suspend fun getProducts(): List<ProductsItem> = productApi.getProducts()

    override suspend fun getProductsByCategories(
        user: String,
        categoryName: String
    ): List<ProductsItem> = productApi.getProductsByCategories(user, categoryName)

    override suspend fun getSaleProducts(): List<ProductsItem> = productApi.getSaleProducts()

    override suspend fun getProductsByName(user: String): List<ProductsItem> =
        productApi.getProductsByName(user)

    override suspend fun getBagProductsByUser(user: String): List<ProductsItem> =
        productApi.getBagProductsByUser(user)

    override suspend fun deleteFromBag(id: Int): CRUDResponse = productApi.deleteFromBag(id)

    override suspend fun getCategoriesByUser(user: String): List<String> =
        productApi.getCategoriesByUser(user)
}