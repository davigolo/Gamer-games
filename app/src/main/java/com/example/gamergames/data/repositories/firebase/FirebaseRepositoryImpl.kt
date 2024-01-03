package com.example.gamergames.data.repositories.firebase

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.datasources.firebase.FireBaseDataSourceImpl
import com.example.gamergames.data.datasources.firebase.FirebaseDataSource
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams
import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames

class FirebaseRepositoryImpl : FirebaseRepository {

    private val firebaseDataSource: FirebaseDataSource by lazy { FireBaseDataSourceImpl() }

    override suspend fun getGames(userId: String): AsyncResult<List<GameBO>> {
        return firebaseDataSource.getGamesList(userId)
    }

    override suspend fun addNewGame(userId: String, params: AddGameUseCaseParams) {
        firebaseDataSource.addNewGame(userId, params)
    }

    override suspend fun deleteGame(userId: String, gameId: String): AsyncResult<Boolean> {
        return firebaseDataSource.deleteGame(userId, gameId)
    }

    override suspend fun updateLikedGameList(
        userId: String,
        id: String,
        data: AddToLikedGames
    ): AsyncResult<Boolean> {
        return firebaseDataSource.updateLikedGameList(userId, id, data)
    }
}