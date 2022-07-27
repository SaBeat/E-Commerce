package com.example.e_commerce.presentation.favorite.favorite

import com.example.e_commerce.data.entities.product.Favorites

interface FavoriteDeleteClickListener {
    fun favoriteDelete(favorites: Favorites)
}