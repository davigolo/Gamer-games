package com.example.gamergames.data.datasources.firebase

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams
import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames

interface FirebaseDataSource {
    suspend fun getGamesList(userId: String): AsyncResult<List<GameBO>>

    suspend fun addNewGame(userId: String, params: AddGameUseCaseParams): AsyncResult<Boolean>

    suspend fun deleteGame(userId: String, gameId: String): AsyncResult<Boolean>

    suspend fun updateLikedGameList(userId: String, id: String, data: AddToLikedGames): AsyncResult<Boolean>

}