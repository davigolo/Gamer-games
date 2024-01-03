package com.example.gamergames.data.repositories.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO

interface RawgRepository {

    suspend fun getGamesByTitle(title: String): AsyncResult<List<AddGameBo>>

    suspend fun getGamesDetails(id: String): AsyncResult<GameDetailsBO>

    suspend fun getGameScreenshots(id: String): AsyncResult<GameScreenshotsBO>

    suspend fun getGamesByDate(date: String, orderBy: String): AsyncResult<List<AddGameBo>>
}