package com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos

import com.google.gson.annotations.SerializedName

class PublishersDTO {
    val id: Int? = null
    val name: String? = null
    val slug: String? = null
    @SerializedName("games_count")
    val gamesCount: Int? = null
    @SerializedName("image_background")
    val imageBackground: String? = null
}
