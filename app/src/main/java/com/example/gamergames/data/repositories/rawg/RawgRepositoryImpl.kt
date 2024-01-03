package com.example.gamergames.data.repositories.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.datasources.rawg.RawgDataSource
import com.example.gamergames.data.datasources.rawg.RawgDataSourceImpl
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO

class RawgRepositoryImpl : RawgRepository {

    private val dataSource: RawgDataSource by lazy { RawgDataSourceImpl() }

    override suspend fun getGamesByTitle(title: String): AsyncResult<List<AddGameBo>> {
        return dataSource.getGamesByTitle(title)
    }

    override suspend fun getGamesDetails(id: String): AsyncResult<GameDetailsBO> {
        return dataSource.getGamesDetails(id)
    }

    override suspend fun getGameScreenshots(id: String): AsyncResult<GameScreenshotsBO> {
        return dataSource.getGameScreenshots(id)
    }

    override suspend fun getGamesByDate(date: String, orderBy: String): AsyncResult<List<AddGameBo>> {
        return  dataSource.getGamesByDate(date, orderBy)
    }

}