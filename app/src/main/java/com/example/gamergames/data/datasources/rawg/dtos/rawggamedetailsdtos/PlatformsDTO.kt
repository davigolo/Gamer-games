package com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos

import com.google.gson.annotations.SerializedName

class PlatformsDTO {
    val platform: GamePlatformDTO? = null
    val requirements: RequirementsDTO? = null
    @SerializedName("relased_at")
    val relasedAt: String? = null
}
