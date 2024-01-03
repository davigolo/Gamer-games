package com.example.gamergames.data.model.firebase.bos

data class GameBO(
    val title: String,
    val description: String,
    val image: String,
    val genre: String,
    val platform: String,
    val gameId: String,
    val gameYear: String,
    val screenshots: ScreenshotsBO,
    val myList: MyListBO,
    val playtime: Int,
    val rating: Double
)