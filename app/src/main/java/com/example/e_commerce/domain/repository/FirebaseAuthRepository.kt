package com.example.e_commerce.domain.repository

import com.example.e_commerce.data.model.AuthModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepository {
    suspend fun getCurrentUserId():String?
    suspend fun signIn(auth: AuthModel):FirebaseUser?
    suspend fun signUpWithEmail(auth:AuthModel):FirebaseUser?
    suspend fun forgotPassword(email:String)
}