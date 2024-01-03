package com.example.gamergames.presentation.views.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamergames.domain.usecases.firebase.AddGameToFavouritesUseCase
import com.example.gamergames.domain.usecases.firebase.AddGameToFavouritesUseCaseImpl
import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames
import com.example.gamergames.presentation.views.login.FirebaseInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyInfoGamesFragmentViewModel : ViewModel() {

    private val addGameToMyListUseCase: AddGameToFavouritesUseCase = AddGameToFavouritesUseCaseImpl()

    private val currentFirebaseUserUId = FirebaseInstance.firebaseInstance.currentUser?.uid
    fun addGameToMyList(id: String, data: AddToLikedGames){
        viewModelScope.launch(Dispatchers.IO) {
            addGameToMyListUseCase.updateLikedGameList(currentFirebaseUserUId ?: "",id, data)
        }
    }
}