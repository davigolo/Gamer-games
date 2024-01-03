package com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos

import com.google.gson.annotations.SerializedName

class GamePlatformDTO {
    val id: Int? = null
    val name: String? = null
    val image: String? = null
    @SerializedName("year_end")
    val yearEnd: String? = null
    @SerializedName("year_start")
    val yearStart: String? = null
    @SerializedName("games_count")
    val gamesCount: Int? = null
    @SerializedName("image_background")
    val imageBackground: String? = null
}
