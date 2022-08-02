package com.example.e_commerce.presentation.purchased

import com.example.e_commerce.data.entities.product.Purchased
import kotlinx.coroutines.flow.Flow

data class PurchasedUiState(
    val error:String?=null,
    val purchased: Flow<List<Purchased>>?=null
)