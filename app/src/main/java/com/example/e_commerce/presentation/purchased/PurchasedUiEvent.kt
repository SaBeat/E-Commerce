package com.example.e_commerce.presentation.purchased

sealed class PurchasedUiEvent {
    data class GetPurchasedProducts(val userId:String):PurchasedUiEvent()
}