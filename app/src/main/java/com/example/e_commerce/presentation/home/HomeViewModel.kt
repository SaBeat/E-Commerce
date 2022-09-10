package com.example.e_commerce.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.GetBasketItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetBasketProductsFromDatabaseUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToCollectionsUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToFavoritesUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.domain.usecase.local.product.GetAllProductFromDatabaseUseCase
import com.example.e_commerce.domain.usecase.local.product.InsertProductToDatabaseUseCase
import com.example.e_commerce.domain.usecase.remote.GetAllCategoriesByUserUseCase
import com.example.e_commerce.domain.usecase.remote.GetAllProductsByNameUseCase
import com.example.e_commerce.domain.usecase.remote.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertProductToFavoritesUseCase: InsertProductToFavoritesUseCase,
    private val insertProductToCollectionsUseCase: InsertProductToCollectionsUseCase,
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase,
    private val getBasketItemsCountUseCase: GetBasketItemsCountUseCase,
    private val getCategoriesUseCase: GetAllCategoriesByUserUseCase,
    private val insertProductToDatabaseUseCase: InsertProductToDatabaseUseCase,
    private val getAllProductsFromDatabaseUseCase: GetAllProductFromDatabaseUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(HomeUIState())
    val _uiState: StateFlow<HomeUIState> = uiState.asStateFlow()

    fun handleEvent(homeUIEvent: HomeUIEvent) {
        when (homeUIEvent) {
            is HomeUIEvent.GetCategories -> {
                getCategories(homeUIEvent.user)
            }
            is HomeUIEvent.GetDiscountProducts -> {
                getDiscountProducts(homeUIEvent.categoryName)
            }
            is HomeUIEvent.GetBasketItemCount -> {
                getBasketItemCount(homeUIEvent.userId)
            }
            is HomeUIEvent.InsertProductToCollection -> {
                insertProductToCollection(homeUIEvent.collections)
            }
            is HomeUIEvent.InsertProductToFavorites -> {
                insertProductToFavorites(homeUIEvent.favorites)
            }
            is HomeUIEvent.InsertProductToDatabase -> {
                insertProductToDatabase(homeUIEvent.products)
            }
            is HomeUIEvent.GetAllProductsFromDatabase -> {
                getAllProductsFromDatabase()
            }
            else -> {}
        }
    }

    private fun insertProductToDatabase(product: List<Product>) {
        viewModelScope.launch {
            insertProductToDatabaseUseCase.invoke(product).collect { resultState ->
                when (resultState) {
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

    private fun getAllProductsFromDatabase(){
        viewModelScope.launch {
            getAllProductsFromDatabaseUseCase.invoke().collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(getProductsFromDatabase = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getCategories(user: String) {
        viewModelScope.launch {
            getCategoriesUseCase.invoke(user).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(getCategories = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getDiscountProducts(categoryName:String){
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

    private fun getBasketItemCount(userId: String) {
        viewModelScope.launch {
            getBasketItemsCountUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(basketItemCount = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }


    private fun insertProductToCollection(collection: Collection) {
        viewModelScope.launch {
            insertProductToCollectionsUseCase.invoke(collection).collect { resultState ->
                when (resultState) {
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

    private fun insertProductToFavorites(favorites: Favorites) {
        viewModelScope.launch {
            insertProductToFavoritesUseCase.invoke(favorites).collect { resultState ->
                when (resultState) {
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