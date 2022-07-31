package com.example.e_commerce.presentation.detail

import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.data.model.CRUDResponse

data class DetailUiState(
    val productModel: Product? = null,
    val error: String? = null,
    val basketItemsCount: Int? = null,
    val response: CRUDResponse? = null
)