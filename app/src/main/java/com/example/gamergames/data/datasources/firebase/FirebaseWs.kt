package com.example.gamergames.data.datasources.firebase


import com.example.gamergames.data.datasources.firebase.dtos.DocumentDTO
import com.example.gamergames.data.datasources.firebase.dtos.GameDTO
import com.example.gamergames.data.datasources.firebase.dtos.PostGameDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FirebaseWs {

    /**Web service with all HTTP petitions to firebase*/
    @GET("users/{id}/games")
    fun getGamesList(@Path("id") userId: String): Call<GameDTO>

    @POST("users/{id}/games")
    fun addNewGame(
        @Path("id") userId: String,
        @Body game: PostGameDTO
    ): Call<DocumentDTO>

    @DELETE("users/{userId}/games/{gameId}")
    fun deleteGame(
        @Path("userId") userId: String,
        @Path("gameId") gameId: String
    ): Call<DocumentDTO>

    @PATCH("users/{userId}/games/{gameId}?updateMask.fieldPaths=myList")
    fun updateFavoriteGame(
        @Path("userId") userId: String,
        @Path("gameId") gameId: String,
        @Body data: PostGameDTO
    ): Call<GameDTO>
}