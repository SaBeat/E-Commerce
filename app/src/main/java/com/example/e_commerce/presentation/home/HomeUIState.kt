package com.example.e_commerce.presentation.home

import com.example.e_commerce.data.entities.product.Product

data class HomeUIState(
    val error: String? = null,
    val products: List<Product>? = null,
    val allProducts: MutableList<Product>? = null,
    val discountProduct: MutableList<Product>? = null,
    val basketItemCount: Int? = null,
    val getCategories: List<String>? = null,
)
