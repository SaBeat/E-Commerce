package com.example.e_commerce.presentation.login_register.login

import com.example.e_commerce.data.entities.product.DiscountProduct
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.data.entities.product.ProductsItem
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.presentation.home.HomeUIEvent

sealed class LoginUiEvent {
    data class Login(var authModel: AuthModel):LoginUiEvent()
    object GetAllProducts: LoginUiEvent()
    data class InsertProductToDatabase(val products:List<Product>):LoginUiEvent()
    data class InsertDiscountProductToDatabase(val products:MutableList<DiscountProduct>):LoginUiEvent()
    object GetCurrentUser : LoginUiEvent()
}