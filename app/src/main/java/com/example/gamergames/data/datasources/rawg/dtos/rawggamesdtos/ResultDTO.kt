package com.example.gamergames.data.datasources.rawg.dtos.rawggamesdtos

import com.google.gson.annotations.SerializedName

class ResultDTO {

    val slug: String? = null
    val name: String? = null
    val playtime: Int? = null
    val platforms: List<PlatformsDTO>? = null
    val stores: List<StoresDTO>? = null
    val released: String? = null
    val tba: Boolean? = null
    @SerializedName("background_image")
    val backgroundImage: String? = null
    val rating: Double? = null
    @SerializedName("rating_top")
    val ratingTop: Int? = null
    val ratings: List<RatingsDTO>? = null
    @SerializedName("ratings_count")
    val ratingsCount: Int? = null
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int? = null
    val added: Int? = null
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatusDTO? = null
    val metacritic: Int? = null
    @SerializedName("suggestions_count")
    val suggestionsCount: Int? = null
    val updated: String? = null
    val id: String? = null
    val score: Float? = null
    val clip: Boolean? = null
    val tags: List<TagsDTO>? = null
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRatingDTO? = null
    @SerializedName("user_game")
    val userGame: Boolean? = null
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null
    @SerializedName("saturated_color")
    val saturatedColor: String? = null
    @SerializedName("dominant_color")
    val dominantColor: String? = null
    @SerializedName("short_screenshots")
    val shortScreenshots: List<ShortScreenshotsDTO>? = null
    @SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatformsDTO>? = null
    val genres: List<GenresDTO>? = null
}