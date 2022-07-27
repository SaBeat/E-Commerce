package com.example.e_commerce.presentation.favorite.favorite

import com.example.e_commerce.data.entities.product.Favorites

sealed class MyFavoritesUiEvent {
     data class GetAllFavorites(val userId:String):MyFavoritesUiEvent()
     data class DeleteFavorites(val favorites: Favorites):MyFavoritesUiEvent()
}