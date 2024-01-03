package com.example.gamergames.domain.usecases.firebase

import com.example.gamergames.AsyncResult

interface RemoveGameUseCase {

    suspend operator fun invoke(userId: String, gameId: String): AsyncResult<Boolean>

}