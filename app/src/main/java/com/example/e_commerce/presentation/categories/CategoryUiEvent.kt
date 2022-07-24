package com.example.e_commerce.presentation.categories

import com.example.e_commerce.data.entities.product.Product

sealed class CategoryUiEvent{
    data class GetCategoriesByName(val categoryName: String):CategoryUiEvent()

}
