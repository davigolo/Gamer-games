package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.repositories.rawg.RawgRepository
import com.example.gamergames.data.repositories.rawg.RawgRepositoryImpl

class GamesDetailsUseCaseImpl: GamesDetailsUseCase {

    private val repositoryRawg: RawgRepository by lazy { RawgRepositoryImpl() }

    override suspend fun getGamesDetails(id: String): AsyncResult<GameDetailsBO> {
        return repositoryRawg.getGamesDetails(id)
    }
}