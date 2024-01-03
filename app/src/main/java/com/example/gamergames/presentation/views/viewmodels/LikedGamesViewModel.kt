package com.example.gamergames.presentation.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamergames.AsyncResult
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.domain.usecases.firebase.GetGamesUseCase
import com.example.gamergames.domain.usecases.firebase.GetGamesUseCaseImpl
import com.example.gamergames.presentation.views.login.FirebaseInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikedGamesViewModel : ViewModel() {

    private val getGamesUseCase: GetGamesUseCase = GetGamesUseCaseImpl()

    private val likedGamesListOnSuccessLiveData: MutableLiveData<AsyncResult<List<GameBO>>> = MutableLiveData()


    private val currentFirebaseUserUId = FirebaseInstance.firebaseInstance.currentUser?.uid
    fun getLikedGamesLiveData() = likedGamesListOnSuccessLiveData as LiveData<AsyncResult<List<GameBO>>>

    fun requestLikedGames() {
        viewModelScope.launch(Dispatchers.IO) {
            val gamesListAsyncResult = getGamesUseCase.getGames(currentFirebaseUserUId ?: "")
            val likedGamesList = gamesListAsyncResult.data?.filter { it.myList.booleanValue }
        //TODO pending
        //likedGamesListOnSuccessLiveData.postValue(likedGamesList)
        }
    }
}
