package com.example.e_commerce.presentation.login_register.login

import com.example.e_commerce.data.entities.user.User

data class LoginUiState (
    var userLogin : User? =null,
    var isLoggedIn : Boolean? = false,
    var currentUser : String? = null
    )