package com.example.e_commerce.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToCollectionsUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToFavoritesUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.domain.usecase.remote.GetAllProductsByNameUseCase
import com.example.e_commerce.domain.usecase.remote.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase,
    private val insertProductToCollectionsUseCase: InsertProductToCollectionsUseCase,
    private val insertProductToFavoritesUseCase: InsertProductToFavoritesUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(CategoryUiState())
    var _uiState:StateFlow<CategoryUiState> = uiState.asStateFlow()

    fun handleEvent(categoryUiEvent: CategoryUiEvent){
        when(categoryUiEvent){
            is CategoryUiEvent.GetCategoriesByName -> {
                getAllCategoriesByName(categoryUiEvent.categoryName)
            }
            is CategoryUiEvent.InsertProductToCollection -> {
                insertProductToCollection(categoryUiEvent.collections)
            }
            is CategoryUiEvent.InsertProductToFavorite -> {
                insertProductToFavorite(categoryUiEvent.favorites)
            }
        }
    }

    fun getAllCategoriesByName(categoryName:String){
        viewModelScope.launch {
            getProductsByCategoriesUseCase.invoke(categoryName).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(products = resultState.data)
                        }
                    }
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun insertProductToCollection(collection: Collection){
        viewModelScope.launch {
            insertProductToCollectionsUseCase.invoke(collection).collect{resultState ->
                when(resultState){
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun insertProductToFavorite(favorites: Favorites){
        viewModelScope.launch {
            insertProductToFavoritesUseCase.invoke(favorites).collect{resultState ->
                when(resultState){
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}