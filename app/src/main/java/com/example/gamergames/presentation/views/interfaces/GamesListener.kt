package com.example.gamergames.presentation.views.interfaces

import com.example.gamergames.data.model.rawg.bos.RawgGameBO

interface GamesListener {
    fun receiveGame(data: RawgGameBO)
}