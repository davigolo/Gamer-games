package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.repositories.firebase.FirebaseRepository
import com.example.gamergames.data.repositories.firebase.FirebaseRepositoryImpl

class RemoveGameUseCaseImpl: RemoveGameUseCase {

    private val firebaseRepository: FirebaseRepository by lazy { FirebaseRepositoryImpl() }

    override suspend fun invoke(userId: String, gameId: String): AsyncResult<Boolean> {
        return firebaseRepository.deleteGame(userId, gameId)
    }

}