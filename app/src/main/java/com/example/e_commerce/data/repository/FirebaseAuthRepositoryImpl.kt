package com.example.e_commerce.data.repository

import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(var firebaseAuth: FirebaseAuth) : FirebaseAuthRepository {
    override suspend fun getCurrentUserId(): String? {
        return try {
            firebaseAuth.currentUser!!.uid
        }catch (e:Exception){
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun signIn(authModel: AuthModel): FirebaseUser? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(authModel.email,authModel.password).await()
            firebaseAuth.currentUser
        }catch (e:Exception){
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun signUpWithEmail(authModel: AuthModel): FirebaseUser? {
        return try{
            firebaseAuth.createUserWithEmailAndPassword(authModel.email,authModel.password).await()
            firebaseAuth.currentUser?.updateProfile(userProfileChangeRequest {
                displayName = authModel.email
            })?.await()
            firebaseAuth.currentUser
        }catch (e:Exception){
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun forgotPassword(email: String) {
       try {
            firebaseAuth.sendPasswordResetEmail(email)
        }catch (e:Exception){
            throw Exception(e.localizedMessage)
        }
    }


}