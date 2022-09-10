package com.example.e_commerce.presentation.home

import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.data.entities.product.Product
import kotlinx.coroutines.flow.Flow
import java.util.*

sealed class HomeUIEvent{
    data class GetDiscountProducts(val categoryName:String):HomeUIEvent()
    data class InsertProductToFavorites(val favorites: Favorites):HomeUIEvent()
    data class InsertProductToCollection(val collections: Collection):HomeUIEvent()
    data class GetBasketItemCount(val userId:String):HomeUIEvent()
    data class GetCategories(val user:String):HomeUIEvent()
    data class InsertProductToDatabase(val products:List<Product>):HomeUIEvent()
    object GetAllProductsFromDatabase : HomeUIEvent()
}
