package com.example.e_commerce.presentation.login_register.login

import com.example.e_commerce.data.entities.product.DiscountProduct
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.data.entities.product.ProductsItem
import com.example.e_commerce.data.entities.user.User

data class LoginUiState (
    var error:String?=null,
    val products: List<Product>? = null,
    var userLogin : User? =null,
    var isLoggedIn : Boolean? = false,
    var currentUser : String? = null,
    )