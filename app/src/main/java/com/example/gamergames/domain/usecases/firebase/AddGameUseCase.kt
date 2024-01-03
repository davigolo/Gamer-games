package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams

interface AddGameUseCase {
    suspend fun addGame(userId: String, params: AddGameUseCaseParams)
}