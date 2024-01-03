package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.AddGameBo

interface SearchGamesByDateUseCase {
    suspend fun searchGamesByDate(dateInterval: String, orderBy: String): AsyncResult<List<AddGameBo>>
}