package com.example.gamergames.data.datasources.rawg.dtos.rawggamesdtos

import com.google.gson.annotations.SerializedName

class RawgGamesDTO {
    var count: Int? = null
    var next: String? = null
    var previous: Boolean? =null
    var results: List<ResultDTO>? = null
    @SerializedName("user_platforms")
    var userPlatforms: Boolean? = null
}