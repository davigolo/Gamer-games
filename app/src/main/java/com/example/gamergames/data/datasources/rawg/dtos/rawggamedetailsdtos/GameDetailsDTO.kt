package com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos

import com.google.gson.annotations.SerializedName

class GameDetailsDTO {
    val id: String? = null
    val slug: String? = null
    val name: String? = null
    @SerializedName("name_original")
    val nameOriginal: String? = null
    val description: String? = null
    val metacritic: Int? = null
    @SerializedName("metacritic_platforms")
    val metacriticPlatforms: List<MetacriticPlatformsDTO>? = null
    val released: String? = null
    val tba: Boolean? = null
    val updated: String? = null
    @SerializedName("background_image")
    val backgroundImage:String? = null
    @SerializedName("background_image_additional")
    val backgroundImageAdditional: String? = null
    val website: String? = null
    val rating: Float? = null
    @SerializedName("rating_top")
    val ratingTop: Int? = null
    val ratings: List<RatingsDTO>? = null
    val reactions: ReactionsDTO? = null
    val added: Int? = null
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatusDTO? = null
    val playtime: Int? = null
    @SerializedName("screenshots_count")
    val screenshotsCount: Int? = null
    @SerializedName("movies_count")
    val moviesCount: Int? = null
    @SerializedName("creators_count")
    val creatorsCount: Int? = null
    @SerializedName("achievements_count")
    val achievementsCount: Int? = null
    @SerializedName("parent_achievements_count")
    val parentAchievementsCount: Int? = null
    @SerializedName("reddit_url")
    val redditUrl: String? = null
    @SerializedName("reddit_name")
    val redditName: String? = null
    @SerializedName("reddit_description")
    val redditDescription: String? = null
    @SerializedName("reddit_logo")
    val redditLogo: String? = null
    @SerializedName("reddit_count")
    val redditCount: Int? = null
    @SerializedName("twitch_count")
    val twitchCount: String? = null
    @SerializedName("youtube_count")
    val youtubeCount: Int? = null
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int? = null
    @SerializedName("ratings_count")
    val ratingsCount: Int? = null
    @SerializedName("suggestions_count")
    val suggestionsCount: Int? = null
    @SerializedName("alternative_names")
    val alternativeNames: List<String>? = null
    @SerializedName("metacritic_url")
    val metacriticUrl: String? = null
    @SerializedName("parents_count")
    val parentsCount: Int? = null
    @SerializedName("additions_count")
    val additionsCount: Int? = null
    @SerializedName("games_series_count")
    val gamesSeriesCount: Int? = null
    @SerializedName("user_game")
    val userGame: Boolean? = null
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null
    @SerializedName("community_rating")
    val communityRating: Double? = null
    @SerializedName("saturated_color")
    val saturatedColor: String? = null
    @SerializedName("dominant_color")
    val dominantColor: String? = null
    @SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatformsDTO>? = null
    val platforms: List<PlatformsDTO>? = null
    val stores: List<StoresDTO?>? = null
    val developers: List<DevelopersDTO?>? = null
    val genres: List<GenresDTO?>? = null
    val tags: List<TagsDTO?>? = null
    val publishers: List<PublishersDTO>? = null
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRatingDTO? = null
    val clip: Boolean? = null
    @SerializedName("description_raw")
    val descriptionRaw: String? = null
}