package com.example.e_commerce.domain.usecase.firebase

import com.example.e_commerce.common.Resource
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val currentUserId = firebaseAuthRepository.getCurrentUserId()
            emit(Resource.Success(currentUserId))
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}