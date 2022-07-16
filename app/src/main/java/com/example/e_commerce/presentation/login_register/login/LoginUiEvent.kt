package com.example.e_commerce.presentation.login_register.login

import com.example.e_commerce.data.model.AuthModel

sealed class LoginUiEvent {
    data class Login(var authModel: AuthModel):LoginUiEvent()
    object GetCurrentUser : LoginUiEvent()
}