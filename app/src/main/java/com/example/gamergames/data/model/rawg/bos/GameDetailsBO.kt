package com.example.gamergames.data.model.rawg.bos

data class GameDetailsBO(
    val id: String,
    val name: String,
    val description: String,
    val gameYear: String,
    val tags: List<TagBO>,
    val image: String,
    val platforms: List<String>,
    val playtime: Int,
    val rating: Int,
    val genres: List<String>
    )
