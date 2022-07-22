package com.example.e_commerce.presentation.home

import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.data.entities.product.Product
import java.util.*

sealed class HomeUIEvent{
    object GetAllProducts:HomeUIEvent()
    data class InsertProductToFavorites(val favorites: Favorites):HomeUIEvent()
    data class InsertProductToCollection(val collections: Collection):HomeUIEvent()
    data class GetDiscountProducts(val categoryName:String):HomeUIEvent()
    data class GetBasketItemCount(val userId:String):HomeUIEvent()
    data class GetCategories(val user:String):HomeUIEvent()
}
