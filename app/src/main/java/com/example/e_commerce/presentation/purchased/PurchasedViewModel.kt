package com.example.e_commerce.presentation.purchased

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resource
import com.example.e_commerce.domain.usecase.local.product.GetPurchasedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasedViewModel @Inject constructor(
  private val getPurchasedProductsUseCase: GetPurchasedProductsUseCase
) : ViewModel(){

    val uiState = MutableStateFlow(PurchasedUiState())
    var _uiState : StateFlow<PurchasedUiState> = uiState

    fun handleEvent(event:PurchasedUiEvent){
        when(event){
            is PurchasedUiEvent.GetPurchasedProducts -> {
                getPurchasedProducts(event.userId)
            }
        }
    }

    fun getPurchasedProducts(userId:String){
        viewModelScope.launch {
            getPurchasedProductsUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(purchased = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}