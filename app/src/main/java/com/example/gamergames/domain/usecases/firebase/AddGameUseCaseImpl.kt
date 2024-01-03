package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.data.repositories.firebase.FirebaseRepository
import com.example.gamergames.data.repositories.firebase.FirebaseRepositoryImpl
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams

class AddGameUseCaseImpl: AddGameUseCase {

    private val firebaseRepository: FirebaseRepository by lazy { FirebaseRepositoryImpl() }
    override suspend fun addGame(userId: String, params: AddGameUseCaseParams) {
        firebaseRepository.
        addNewGame(userId, params)
    }
}