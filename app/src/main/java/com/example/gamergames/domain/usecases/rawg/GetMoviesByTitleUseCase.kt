package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.AddGameBo

interface GetMoviesByTitleUseCase {

    suspend fun getMoviesByTitle(title: String): AsyncResult<List<AddGameBo>>
}