package com.example.e_commerce.presentation.login_register.register

data class RegisterUiState(
    val error : String? =null,
    val isRegister : Boolean? =false,
    val dbError:String? = null,
    val currentUser:String?=null,
    val firebaseUser:String?=null
)