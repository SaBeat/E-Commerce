package com.example.e_commerce.presentation.favorite.favorite

import com.example.e_commerce.data.entities.product.Favorites
import kotlinx.coroutines.flow.Flow

data class MyFavoritesUiState(
    val error:String? = null,
    val favorites: Flow<List<Favorites>>?=null
)
