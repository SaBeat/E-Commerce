package com.example.e_commerce.domain.usecase.firebase

import com.example.e_commerce.common.Resource
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(email:String) = flow {
        emit(Resource.Loading)
        try{
            val forgotPass = firebaseAuthRepository.forgotPassword(email)
            emit(Resource.Success(forgotPass))

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}