package com.example.e_commerce.presentation.login_register.register

import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.data.model.AuthModel
import com.google.firebase.auth.FirebaseAuth

sealed class RegisterUiEvent {
    data class InsertuserToDatabase(val user: User): RegisterUiEvent()
    data class CreatUser(val authModel: AuthModel):RegisterUiEvent()
}