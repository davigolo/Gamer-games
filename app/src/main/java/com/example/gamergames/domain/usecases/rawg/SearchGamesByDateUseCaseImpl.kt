package com.example.gamergames.domain.usecases.rawg

import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO
import com.example.gamergames.data.model.rawg.bos.RawgGameBO
import com.example.gamergames.data.repositories.rawg.RawgRepository
import com.example.gamergames.data.repositories.rawg.RawgRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchGamesByDateUseCaseImpl : SearchGamesByDateUseCase {

    private val repository: RawgRepository by lazy { RawgRepositoryImpl() }
    private val getGameScreenshotUseCaseImpl by lazy { GameScreenshotsUseCaseImpl() }
    private val getGameDetailsUseCaseImpl by lazy { GamesDetailsUseCaseImpl() }

    fun interface SearchGamesUseCaseListener {
        fun onGameListReady(data: AsyncResult<List<RawgGameBO>>)
    }

    override suspend fun searchGamesByDate(
        dateInterval: String,
        orderBy: String
    ): AsyncResult<List<AddGameBo>> {
        return repository.getGamesByDate(dateInterval, orderBy)
    }

    /**Function to get a list of games from a given date to another date, firstly it get a list of games ids,
     with these ids we get every game description and screenshots, finally we combine all the data in one BO item*/
    suspend fun searchGamesByDateUseCase(
        dateInterval: String, orderBy: String, coroutineScope: CoroutineScope,
        listener: SearchGamesUseCaseListener
    ) {
        coroutineScope.launch(Dispatchers.IO) {

            val searchGamesByDateAsyncResult = searchGamesByDate(dateInterval, orderBy)

            //Handling HTTP petition result
            when (searchGamesByDateAsyncResult.state) {
                AsyncResult.STATE.SUCCESS -> {
                    val gamesIdList =
                        searchGamesByDate(dateInterval, orderBy).data?.map { it.gameId }

                    val deferredImageGame = async {
                        //Game list with screenshots
                        val gameScreenshotsList = arrayListOf<AsyncResult<GameScreenshotsBO>>()
                        gamesIdList?.forEach { gameId ->
                            gameScreenshotsList.add(
                                getGameScreenshotUseCaseImpl.getGameScreenshots(gameId)
                            )
                        }
                        gameScreenshotsList
                    }

                    val deferredGameDetails = async {
                        //Games list with details
                        val gameDetailsList = arrayListOf<AsyncResult<GameDetailsBO>>()
                        gamesIdList?.forEach { gameId ->
                            gameDetailsList.add(
                                getGameDetailsUseCaseImpl.getGamesDetails(
                                    gameId
                                )
                            )
                        }
                        gameDetailsList
                    }

                    val gameListWithScreenshots = deferredImageGame.await()
                    val gameListWithGameDetails = deferredGameDetails.await()

                    //Final list with game description and screenshots
                    val gameBoList = ArrayList<RawgGameBO>()
                    gamesIdList?.forEachIndexed { index, _ ->
                        gameBoList.add(
                            RawgGameBO(
                                gameListWithGameDetails[index].data,
                                gameListWithScreenshots[index].data
                            )
                        )
                    }
                    listener.onGameListReady(AsyncResult.success(gameBoList))
                }

                AsyncResult.STATE.ERROR -> {
                    val onErrorList = arrayListOf(RawgGameBO())
                    listener.onGameListReady(
                        AsyncResult.error(
                            searchGamesByDateAsyncResult.error,
                            onErrorList
                        )
                    )
                }

                else -> {
                    listener.onGameListReady(AsyncResult.exception(searchGamesByDateAsyncResult.exception))
                }
            }
        }
    }

}