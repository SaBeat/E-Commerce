package com.example.e_commerce.presentation.login_register.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.DiscountProduct
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.data.entities.product.ProductsItem
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.e_commerce.domain.usecase.firebase.LogInUseCase
import com.example.e_commerce.domain.usecase.local.product.InsertDiscountProductToDatabaseUseCase
import com.example.e_commerce.domain.usecase.local.product.InsertProductToDatabaseUseCase
import com.example.e_commerce.domain.usecase.remote.GetAllProductsByNameUseCase
import com.example.e_commerce.domain.usecase.remote.GetProductsByCategoriesUseCase
import com.example.e_commerce.presentation.home.HomeUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val getCurrentUserUseCase: GetCurrentUserIdUseCase,
    private val getAllProductsFromUseCase: GetAllProductsByNameUseCase,
    private val insertProductToDatabaseUseCase: InsertProductToDatabaseUseCase,
    private val insertDiscountproductToDatabaseUseCase : InsertDiscountProductToDatabaseUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = uiState.asStateFlow()

    fun handleEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {
            is LoginUiEvent.Login -> {
                login(loginUiEvent.authModel)
            }
            is LoginUiEvent.GetCurrentUser -> {
                getCurrentUser()
            }
            is LoginUiEvent.GetAllProducts -> {
                getAllProducts()
            }
            is LoginUiEvent.InsertProductToDatabase -> {
                insertProductToDatabase(loginUiEvent.products)
            }
            is LoginUiEvent.InsertDiscountProductToDatabase -> {
                insertDiscountProductToDatabase(loginUiEvent.products)
            }
        }
    }

    private fun insertDiscountProductToDatabase(productItem:MutableList<DiscountProduct>) {
        viewModelScope.launch {
            insertDiscountproductToDatabaseUseCase.invoke(productItem).collect{resultState ->
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

    private fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsFromUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(products = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun login(authModel: AuthModel) {
        viewModelScope.launch {
            logInUseCase.invoke(authModel).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isLoggedIn = true)
                        }
                    }

                    else -> {
                        Log.v("SABIT", "Login Error")
                    }
                }
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(currentUser = result.data)
                        }
                    }
                    else -> {
                        Log.v("SABIT", "Login GetCurrentUser Error")
                    }
                }
            }
        }
    }


}