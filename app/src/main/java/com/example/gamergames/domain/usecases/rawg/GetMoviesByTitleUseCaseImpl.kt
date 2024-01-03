package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.repositories.rawg.RawgRepository
import com.example.gamergames.data.repositories.rawg.RawgRepositoryImpl

class GetMoviesByTitleUseCaseImpl: GetMoviesByTitleUseCase {

    private val repository: RawgRepository by lazy { RawgRepositoryImpl() }

    override suspend fun getMoviesByTitle(title: String): AsyncResult<List<AddGameBo>> {
        return repository.getGamesByTitle(title)
    }

}