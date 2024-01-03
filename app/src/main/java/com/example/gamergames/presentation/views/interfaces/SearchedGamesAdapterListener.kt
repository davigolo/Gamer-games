package com.example.gamergames.presentation.views.interfaces

import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO

interface SearchedGamesAdapterListener {
    fun onGameClick(gameDetailsBO: GameDetailsBO, gameScreenshotsBO: GameScreenshotsBO)
}