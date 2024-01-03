package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO

interface GameScreenshotsUseCase {
    suspend fun getGameScreenshots(id: String): AsyncResult<GameScreenshotsBO>

}