package com.example.gamergames.domain.usecases.firebase.params

data class AddGameUseCaseParams(
    val title: String,
    val description: String?,
    val image: String?,
    val genre: String,
    val platform: String,
    val year: String,
    val screenshots: List<String>,
    val myList: Boolean,
    val playtime: Int,
    val rating: Double
)
