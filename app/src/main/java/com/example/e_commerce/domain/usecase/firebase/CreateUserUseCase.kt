package com.example.e_commerce.domain.usecase.firebase

import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(authModel: AuthModel) = flow {
        emit(Resource.Loading)
        try {
            val createUser = firebaseAuthRepository.signUpWithEmail(authModel)
            emit(Resource.Success(createUser))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}