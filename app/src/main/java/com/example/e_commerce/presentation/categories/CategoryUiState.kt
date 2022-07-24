package com.example.e_commerce.presentation.categories

import com.example.e_commerce.data.entities.product.Product

data class CategoryUiState(
    val error:String? =null,
    val products:MutableList<Product>? =null
)
