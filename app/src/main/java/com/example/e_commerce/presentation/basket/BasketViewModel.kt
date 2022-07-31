package com.example.e_commerce.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.GetBasketProductsFromDatabaseUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetFavoritesProductsFromDatabaseUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Basket
import com.example.e_commerce.data.entities.product.Purchased
import com.example.e_commerce.domain.usecase.local.product.DeleteBasketProductUseCase
import com.example.e_commerce.domain.usecase.local.product.InsertProductToPurchasedUseCase
import com.example.e_commerce.domain.usecase.remote.DeleteProductFromBagUseCase
import com.example.e_commerce.domain.usecase.remote.GetBagProductsByUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
 private val getBasketProductsFromDatabaseUseCase: GetBasketProductsFromDatabaseUseCase,
 private val deleteProductFromBagUseCase: DeleteProductFromBagUseCase,
 private val deleteBasketProductUseCase: DeleteBasketProductUseCase,
 private val getBagProductsByUserUseCase: GetBagProductsByUserUseCase,
 private val insertProductToPurchasedUseCase: InsertProductToPurchasedUseCase
) : ViewModel(){

    val uiState = MutableStateFlow(BasketUiState())
    var _uiState:StateFlow<BasketUiState> = uiState.asStateFlow()

    fun handleEvent(basketUiEvent: BasketUiEvent){
        when(basketUiEvent){
            is BasketUiEvent.GetAllBasketItem -> {
                getAllBasketItem(basketUiEvent.userId)
            }
            is BasketUiEvent.GetBagBasketFromApi -> {
                getBagBasketFromApi(basketUiEvent.userId)
            }
            is BasketUiEvent.DeleteBasketItem -> {
                deleteBasketItem(basketUiEvent.basket)
            }
            is BasketUiEvent.DeleteProductFromBag -> {
                deleteProductFromBag(basketUiEvent.id)
            }
            is BasketUiEvent.InsertPurchasedToDatabase -> {
                insertPurchasedToDatabase(basketUiEvent.purchased)
            }
        }
    }

    fun getAllBasketItem(userId:String){
        viewModelScope.launch {
            getBasketProductsFromDatabaseUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(basketItem = resultState.data)
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

    fun getBagBasketFromApi(userId: String){
        viewModelScope.launch {
            getBagProductsByUserUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(bagProducts = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteBasketItem(basket: Basket){
        viewModelScope.launch {
            deleteBasketProductUseCase.invoke(basket).collect{resultState ->
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

    fun deleteProductFromBag(id:Int){
        viewModelScope.launch{
            deleteProductFromBagUseCase.invoke(id).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(deleteBag = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun insertPurchasedToDatabase(purchased: Purchased){
        viewModelScope.launch {
            insertProductToPurchasedUseCase.invoke(purchased).collect{resultState ->
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