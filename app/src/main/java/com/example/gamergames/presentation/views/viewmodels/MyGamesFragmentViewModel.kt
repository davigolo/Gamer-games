package com.example.gamergames.presentation.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.domain.usecases.firebase.GetGamesUseCase
import com.example.gamergames.domain.usecases.firebase.GetGamesUseCaseImpl
import com.example.gamergames.domain.usecases.firebase.RemoveGameUseCase
import com.example.gamergames.domain.usecases.firebase.RemoveGameUseCaseImpl
import com.example.gamergames.presentation.views.login.FirebaseInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyGamesFragmentViewModel : ViewModel() {

    private val gamesListLiveData: MutableLiveData<AsyncResult<List<GameBO>>> = MutableLiveData()

    private val onRemoveGameLiveData: MutableLiveData<String> = MutableLiveData()

    private val getGamesUseCase: GetGamesUseCase = GetGamesUseCaseImpl()

    private val removeGameUseCase: RemoveGameUseCase = RemoveGameUseCaseImpl()

    private val currentFirebaseUserUId = FirebaseInstance.firebaseInstance.currentUser?.uid

    private val isDataLoadedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getDataLoadingState() = isDataLoadedLiveData as LiveData<Boolean>

    fun getGamesLiveData() = gamesListLiveData as LiveData<AsyncResult<List<GameBO>>>

    fun getOnSuccessRemoveGameLiveData() = onRemoveGameLiveData as LiveData<String>

    fun requestGamesList() {

        viewModelScope.launch(Dispatchers.IO) {
            val gameListAsyncResult = getGamesUseCase.getGames(currentFirebaseUserUId ?: "")
            withContext(Dispatchers.Main) {
                gamesListLiveData.postValue(gameListAsyncResult)
                isDataLoadedLiveData.postValue(true)
            }
        }
    }

    fun removeGame(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO loading error etc..aqui o en ui, no puedo en ui, tengo que hacer post del id
            val isGameRemovedAsyncResult = removeGameUseCase(currentFirebaseUserUId ?: "", id)
            if (isGameRemovedAsyncResult.data!!) {
                onRemoveGameLiveData.postValue(id)
            }
        }
    }
}