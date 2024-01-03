package com.example.gamergames.data.datasources.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO

interface RawgDataSource {

    suspend fun getGamesByTitle(title: String): AsyncResult<List<AddGameBo>>
    suspend fun getGamesDetails(id: String): AsyncResult<GameDetailsBO>
    suspend fun getGamesByDate(dateInterval: String, orderBy: String): AsyncResult<List<AddGameBo>>
    suspend fun getGameScreenshots(id: String): AsyncResult<GameScreenshotsBO>

}