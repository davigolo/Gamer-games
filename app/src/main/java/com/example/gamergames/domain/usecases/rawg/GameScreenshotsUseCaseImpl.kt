package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO
import com.example.gamergames.data.repositories.rawg.RawgRepository
import com.example.gamergames.data.repositories.rawg.RawgRepositoryImpl

class GameScreenshotsUseCaseImpl: GameScreenshotsUseCase {
    private val repositoryRawg: RawgRepository by lazy { RawgRepositoryImpl() }
    override suspend fun getGameScreenshots(id: String): AsyncResult<GameScreenshotsBO> {
        return repositoryRawg.getGameScreenshots(id)

    }
}