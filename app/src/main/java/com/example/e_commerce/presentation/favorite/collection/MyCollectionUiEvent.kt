package com.example.e_commerce.presentation.favorite.collection

import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.presentation.favorite.favorite.MyFavoritesUiEvent

sealed class MyCollectionUiEvent {
    data class GetAllCollections(val userId:String):MyCollectionUiEvent()
    data class DeleteCollection(val collection: Collection):MyCollectionUiEvent()
}