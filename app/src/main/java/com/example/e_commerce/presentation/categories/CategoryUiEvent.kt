package com.example.e_commerce.presentation.categories

import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.data.entities.product.Product

sealed class CategoryUiEvent{
    data class GetCategoriesByName(val categoryName: String):CategoryUiEvent()
    data class InsertProductToFavorite(val favorites:Favorites) : CategoryUiEvent()
    data class InsertProductToCollection(val collections:Collection):CategoryUiEvent()

}
