package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.data.repositories.firebase.FirebaseRepository
import com.example.gamergames.data.repositories.firebase.FirebaseRepositoryImpl
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams

class GetGamesUseCaseImpl : GetGamesUseCase {

    private val firebaseRepository: FirebaseRepository by lazy { FirebaseRepositoryImpl() }

    override suspend fun getGames(userId: String,): AsyncResult<List<GameBO>> {
        return firebaseRepository.getGames(userId)
    }
}