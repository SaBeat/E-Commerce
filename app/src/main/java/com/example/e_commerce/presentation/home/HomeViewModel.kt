package com.example.e_commerce.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.GetBasketItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToCollectionsUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToFavoritesUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.domain.usecase.remote.GetAllCategoriesByUserUseCase
import com.example.e_commerce.domain.usecase.remote.GetAllProductsByNameUseCase
import com.example.e_commerce.domain.usecase.remote.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertProductToFavoritesUseCase: InsertProductToFavoritesUseCase,
    private val insertProductToCollectionsUseCase: InsertProductToCollectionsUseCase,
    private val getAllProductsFromUseCase: GetAllProductsByNameUseCase,
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase,
    private val getBasketItemsCountUseCase: GetBasketItemsCountUseCase,
    private val getCategoriesUseCase: GetAllCategoriesByUserUseCase
) : ViewModel(){

    private val uiState = MutableStateFlow(HomeUIState())
    val _uiState:StateFlow<HomeUIState> = uiState.asStateFlow()

    fun handleEvent(homeUIEvent: HomeUIEvent){
        when(homeUIEvent){
            is HomeUIEvent.GetCategories -> {
                getCategories(homeUIEvent.user)
            }
            is HomeUIEvent.GetAllProducts -> {
                getAllProducts()
            }
            is HomeUIEvent.GetBasketItemCount -> {
                getBasketItemCount(homeUIEvent.userId)
            }
            is HomeUIEvent.GetDiscountProducts -> {
                getDiscountProducts(homeUIEvent.categoryName)
            }
            is HomeUIEvent.InsertProductToCollection -> {
                insertProductToCollection(homeUIEvent.collections)
            }
            is HomeUIEvent.InsertProductToFavorites -> {
                insertProductToFavorites(homeUIEvent.favorites)
            }
        }
    }

    fun getCategories(user:String){
        viewModelScope.launch {
            getCategoriesUseCase.invoke(user).collect{resultState->
                when(resultState){
                    is Resource.Success ->{
                        uiState.update { state->
                           state.copy(getCategories = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun getAllProducts(){
        viewModelScope.launch {
            getAllProductsFromUseCase.invoke().collect{resultState ->
                when(resultState){
                    is Resource.Success ->{
                        uiState.update {state->
                            state.copy(products = resultState.data)
                        }
                    }
                    else ->{}
                }
            }
        }
    }

    fun getBasketItemCount(userId:String){
        viewModelScope.launch {
            getBasketItemsCountUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success ->{
                        uiState.update {state ->
                            state.copy(basketItemCount = resultState.data)
                        }
                    }
                    else ->{}
                }
            }
        }
    }

    fun getDiscountProducts(categoryName:String){
        viewModelScope.launch {
            getProductsByCategoriesUseCase.invoke(categoryName).collect{resultState ->
                when(resultState){
                    is Resource.Success ->{
                        uiState.update {state ->
                            state.copy(allProducts = resultState.data)
                        }
                    }
                    else ->{}
                }
            }
        }
    }

    fun insertProductToCollection(collection: Collection){
        viewModelScope.launch {
            insertProductToCollectionsUseCase.invoke(collection).collect{resultState ->
                when(resultState){
                    is Resource.Error ->{
                        uiState.update {state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun insertProductToFavorites(favorites: Favorites){
        viewModelScope.launch {
            insertProductToFavoritesUseCase.invoke(favorites).collect{resultState ->
                when(resultState){
                    is Resource.Error ->{
                        uiState.update {state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}