package com.example.e_commerce.presentation.favorite.collection

import com.example.e_commerce.data.entities.product.Collection
import kotlinx.coroutines.flow.Flow

data class MyCollectionUiState(
    val collections: Flow<List<Collection>>? = null,
    val error: String? = null
)