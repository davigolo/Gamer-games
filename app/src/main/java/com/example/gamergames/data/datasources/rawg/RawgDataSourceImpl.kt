package com.example.gamergames.data.datasources.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.datasources.rawg.mappers.toBo
import com.example.gamergames.data.datasources.rawg.mappers.toGameScreenshotsBO
import com.example.gamergames.data.datasources.rawg.mappers.toRawgGamesDetailsBO
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**Data Source implementation where we receive all the data from Firebase and is converted to BO items*/
class RawgDataSourceImpl : RawgDataSource {

    /**Retrofit builder*/
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val remoteRawg by lazy { retrofit.create(RawgWs::class.java) }

    override suspend fun getGamesByTitle(title: String): AsyncResult<List<AddGameBo>> {
        return try {

            val response = remoteRawg.getGamesByTitle(title).execute()
            val data = response.body().toBo()

            if (response.errorBody() == null) {
                return AsyncResult.success(data)
            } else {
                return AsyncResult.error(
                    "Error: " + response.code().toString(),
                    data
                )
            }
        } catch (exception: Exception) {
            AsyncResult.exception(exception)
        }
    }

    override suspend fun getGamesDetails(id: String): AsyncResult<GameDetailsBO> {
        return try {

            val response = remoteRawg.getGameInfo(id).execute()
            val data = response.body().toRawgGamesDetailsBO()

            if (response.errorBody() == null) {
                return AsyncResult.success(data)
            } else {
                return AsyncResult.error(
                     "Error: " + response.code().toString(),
                    data
                )
            }
        } catch (exception: Exception) {
            AsyncResult.exception(exception)
        }
    }

    override suspend fun getGamesByDate(
        dateInterval: String,
        orderBy: String
    ): AsyncResult<List<AddGameBo>> {
        return try {

            val response = remoteRawg.gameReleasesFromDateToDate(dateInterval, orderBy).execute()
            val data = response.body().toBo()

            if (response.errorBody() == null) {
                return AsyncResult.success(data)
            } else {
                return AsyncResult.error(
                    "Error: " + response.code().toString(),
                    data
                )
            }
        } catch (exception: Exception) {
            AsyncResult.exception(exception)
        }
    }

    override suspend fun getGameScreenshots(id: String): AsyncResult<GameScreenshotsBO> {
        return try {

            val response = remoteRawg.getGameScreenshots(id).execute()
            val data = response.body().toGameScreenshotsBO()

            if (response.errorBody() == null) {
                return AsyncResult.success(data)
            } else {
                return AsyncResult.error(
                    "Error: " + response.code().toString(),
                    data
                )
            }
        } catch (exception: Exception) {
            AsyncResult.exception(exception)
        }
    }

}