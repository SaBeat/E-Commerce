package com.example.e_commerce.presentation.forgot_password

 sealed class ForgotUiEvent {
    data class ResetPassword(val email:String):ForgotUiEvent()
}