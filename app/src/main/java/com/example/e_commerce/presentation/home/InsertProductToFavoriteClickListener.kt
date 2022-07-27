package com.example.e_commerce.presentation.home

import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.data.entities.product.Product

interface InsertProductToFavoriteClickListener {
    fun insertFavorite(product:Product)
}