package com.example.e_commerce.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.GetBasketItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToBasketUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Basket
import com.example.e_commerce.domain.usecase.remote.AddProductToBagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val insertProductToBasketUseCase: InsertProductToBasketUseCase,
    private val addProductToBagUseCase: AddProductToBagUseCase,
    private val getBasketItemsCountUseCase: GetBasketItemsCountUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(DetailUiState())
    var _uiState: StateFlow<DetailUiState> = uiState.asStateFlow()

    fun handleEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.InsertProductToBasket -> {
                insertProductToBasket(event.basketItem)
            }
            is DetailUiEvent.GetBasketItemsCount -> {
                getBasketItemCount(event.userId)
            }
            is DetailUiEvent.AddProductToBasket -> {
                event.user?.let {user->
                    event.title?.let { title ->
                        event.price?.let { price ->
                            event.description?.let { desc ->
                                event.category?.let { category ->
                                    event.image?.let { image ->
                                        event.rate?.let { rate ->
                                            event.count?.let { count ->
                                                event.sale_state?.let { sale_status ->
                                                    addProductToBag(
                                                        user,
                                                        title,
                                                        price,
                                                        desc,
                                                        category,
                                                        image,
                                                        rate,
                                                        count,
                                                        sale_status
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun insertProductToBasket(basket: Basket) {
        viewModelScope.launch {
            insertProductToBasketUseCase.invoke(basket).collect { resultState ->
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

    fun getBasketItemCount(userId: String) {
        viewModelScope.launch {
            getBasketItemsCountUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(basketItemsCount = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun addProductToBag(
        user: String,
        title: String,
        price: Double,
        description: String,
        category: String,
        image: String,
        rate: Double,
        count: Int,
        sale_state: Int
    ) {
        viewModelScope.launch {
            addProductToBagUseCase.invoke(
                user, title, price, description, category, image, rate, count, sale_state
            ).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(response = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}