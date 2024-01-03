package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.data.repositories.firebase.FirebaseRepository
import com.example.gamergames.data.repositories.firebase.FirebaseRepositoryImpl
import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames

class AddGameToFavouritesUseCaseImpl: AddGameToFavouritesUseCase {
    private val firebaseRepository: FirebaseRepository by lazy { FirebaseRepositoryImpl() }
    override suspend fun updateLikedGameList(userId: String, id: String, data: AddToLikedGames) {
        firebaseRepository.updateLikedGameList( userId, id, data)
    }


}