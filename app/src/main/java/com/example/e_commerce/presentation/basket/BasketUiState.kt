package com.example.e_commerce.presentation.basket

import com.example.e_commerce.data.entities.product.Basket
import com.example.e_commerce.data.model.CRUDResponse
import kotlinx.coroutines.flow.Flow

data class BasketUiState(
    val error:String?=null,
    val basketItem: Flow<List<Basket>>?=null,
    val deleteBag:CRUDResponse?=null,
    val bagProducts:MutableList<Basket>?=null
    )
