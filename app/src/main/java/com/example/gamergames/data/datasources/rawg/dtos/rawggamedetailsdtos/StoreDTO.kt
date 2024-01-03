package com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos

import com.google.gson.annotations.SerializedName

class StoreDTO {
    val id: Int? = null
    val name: String? = null
    val slug: String? = null
    val domain: String? = null
    @SerializedName("games_count")
    val gamesCount: String? = null
    @SerializedName("image_backgroung")
    val imageBackgroung: String? = null
}
