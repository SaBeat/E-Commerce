package com.example.e_commerce.presentation.home

import com.example.e_commerce.data.entities.product.Product
import kotlinx.coroutines.flow.Flow

data class HomeUIState(
    val error: String? = null,
    val products: List<Product>? = null,
    val allProducts: MutableList<Product>? = null,
    val getProductsFromDatabase: Flow<List<Product>>? = null,
    val discountProduct: MutableList<Product>? = null,
    val basketItemCount: Int? = null,
    val getCategories: List<String>? = null,
)
