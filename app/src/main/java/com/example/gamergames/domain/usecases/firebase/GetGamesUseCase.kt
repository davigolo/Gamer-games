package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams

interface GetGamesUseCase {
    suspend fun getGames(userId: String,): AsyncResult<List<GameBO>>
}