package com.example.gamergames.presentation.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO
import com.example.gamergames.domain.usecases.firebase.AddGameUseCase
import com.example.gamergames.domain.usecases.firebase.AddGameUseCaseImpl
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams
import com.example.gamergames.domain.usecases.rawg.GameScreenshotsUseCase
import com.example.gamergames.domain.usecases.rawg.GameScreenshotsUseCaseImpl
import com.example.gamergames.domain.usecases.rawg.GamesDetailsUseCase
import com.example.gamergames.domain.usecases.rawg.GamesDetailsUseCaseImpl
import com.example.gamergames.domain.usecases.rawg.GetMoviesByTitleUseCase
import com.example.gamergames.domain.usecases.rawg.GetMoviesByTitleUseCaseImpl
import com.example.gamergames.presentation.views.login.FirebaseInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddGameFragmentViewModel : ViewModel() {

    private val firebaseInstance = FirebaseInstance.firebaseInstance

    private val addGameUseCase: AddGameUseCase = AddGameUseCaseImpl()
    private val getMovieByTitleUseCase: GetMoviesByTitleUseCase = GetMoviesByTitleUseCaseImpl()
    private val gameScreenshotsUseCase: GameScreenshotsUseCase = GameScreenshotsUseCaseImpl()
    private val gamesDetailsUseCase: GamesDetailsUseCase = GamesDetailsUseCaseImpl()
    private var selectedGame: GameDetailsBO? = null

    private val extraDataMovieLiveData: MutableLiveData<GameDetailsBO> = MutableLiveData()

    private val gamesListLiveData: MutableLiveData<AsyncResult<List<AddGameBo>>> = MutableLiveData()

    private val isDataLoadedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val gameScreenshotsLiveData: MutableLiveData<AsyncResult<GameScreenshotsBO>> = MutableLiveData()


    fun getExtraDataMovieLiveData() = extraDataMovieLiveData as LiveData<GameDetailsBO>
    fun getGameScreenshotsLiveData() = gameScreenshotsLiveData as LiveData<AsyncResult<GameScreenshotsBO>>
    fun getDataLoadingState() = isDataLoadedLiveData as LiveData<Boolean>
    fun getGamesListLiveData() = gamesListLiveData as LiveData<AsyncResult<List<AddGameBo>>>


    fun setCurrentGame(movie: GameDetailsBO) {
        selectedGame = movie
    }

    fun getSelectedGameImage(): String {
        return selectedGame?.image!!
    }

    fun addGame(
        tvGameTitle: String,
        tvGameDesctiption: String,
        gameImage: String,
        gamePlatform: String,
        gameGenre: String,
        year: String,
        screenshots: List<String>,
        myList: Boolean,
        playtime: Int,
        rating: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val params = AddGameUseCaseParams(
                tvGameTitle,
                tvGameDesctiption,
                gameImage,
                gamePlatform,
                gameGenre,
                year,
                screenshots,
                myList,
                playtime,
                rating
            )
            addGameUseCase.addGame(firebaseInstance.currentUser?.uid ?: "", params)
        }
    }


    fun requestGameList(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val gameListAsyncResult = getMovieByTitleUseCase.getMoviesByTitle(title)
            withContext(Dispatchers.Main) {
                gamesListLiveData.postValue(gameListAsyncResult)
                isDataLoadedLiveData.postValue(true)
            }
        }
    }

    fun getGameDetails(gameDetails: GameDetailsBO) {
        viewModelScope.launch(Dispatchers.IO) {

            val deferredDetailsAsyncResult =
                async { gamesDetailsUseCase.getGamesDetails(gameDetails.id) }

            val gameDetailsAsyncResult = deferredDetailsAsyncResult.await()
            val newGame = gameDetailsAsyncResult.data?.copy(
                description = gameDetailsAsyncResult.data.description,
                gameYear = gameDetailsAsyncResult.data.gameYear,
                tags = gameDetailsAsyncResult.data.tags,
                platforms = gameDetailsAsyncResult.data.platforms,
                playtime = gameDetailsAsyncResult.data.playtime,
                rating = gameDetailsAsyncResult.data.rating,
                genres = gameDetailsAsyncResult.data.genres
            )
            newGame?.let { newGame -> extraDataMovieLiveData.postValue(newGame) }
        }
    }

    fun getGameScreenshots(gameId: GameDetailsBO) {
        viewModelScope.launch(Dispatchers.IO) {
            val gameScreenshotsAsyncResult = gameScreenshotsUseCase.getGameScreenshots(gameId.id)
            gameScreenshotsLiveData.postValue(gameScreenshotsAsyncResult)
        }
    }

    fun formatGameRating(rating: Double): Double {
        return (rating * 5) / 100
    }
}


