package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames

interface AddGameToFavouritesUseCase {
    suspend fun updateLikedGameList(userId: String, id: String, data: AddToLikedGames)
}