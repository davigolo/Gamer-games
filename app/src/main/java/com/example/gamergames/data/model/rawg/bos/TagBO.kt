package com.example.gamergames.data.model.rawg.bos

data class TagBO (
    val id: Int,
    val name: String,
    val slug: String? = null,
    val language: String? = null,
    val gamesCount: Int = 0,
    val imageBackground: String? = null
)