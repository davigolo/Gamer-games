package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO

interface GamesDetailsUseCase {
    suspend fun getGamesDetails(id: String): AsyncResult<GameDetailsBO>

}