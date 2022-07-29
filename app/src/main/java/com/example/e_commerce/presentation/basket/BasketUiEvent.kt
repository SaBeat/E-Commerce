package com.example.e_commerce.presentation.basket

import com.example.e_commerce.data.entities.product.Basket

sealed class BasketUiEvent{
    data class GetAllBasketItem(val userId:String):BasketUiEvent()
    data class DeleteBasketItem(val basket: Basket):BasketUiEvent()
    data class GetBagBasketFromApi(val userId: String):BasketUiEvent()
    data class DeleteProductFromBag(val id:Int):BasketUiEvent()

}
