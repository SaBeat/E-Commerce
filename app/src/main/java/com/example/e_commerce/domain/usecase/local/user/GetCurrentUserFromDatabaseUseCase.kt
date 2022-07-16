package com.example.e_commerce.domain.usecase.local.user

import com.example.e_commerce.common.Resource
import com.example.e_commerce.di.IoDispatcher
import com.example.e_commerce.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentUserFromDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(userId:String) = flow {
        emit(Resource.Loading)
        try {
            val getCurrentUserFromDatabaseUseCase = localRepository.getCurrentUser(userId)
            emit(Resource.Success(getCurrentUserFromDatabaseUseCase))
        }catch (e:Exception){
            throw Exception(e.localizedMessage)
        }
    }.flowOn(ioDispatcher)
}