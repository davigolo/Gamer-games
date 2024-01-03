package com.example.gamergames.data.datasources.rawg


import com.example.gamergames.data.datasources.rawg.dtos.gamescreenshots.GameScreenshotsDTO
import com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos.GameDetailsDTO
import com.example.gamergames.data.datasources.rawg.dtos.rawggamesdtos.RawgGamesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgWs {

    /**Web Service with all HTTP petitions to RAWG API*/
    companion object {
        private const val API_KEY = "26c97db97ffc4dd5b39ceb1b6eda2177"
    }

    @GET("games?key=${API_KEY}")
    fun getGamesByTitle(
        @Query("search") gameTitle: String
    ): Call<RawgGamesDTO>

    @GET("games/{id}")
    fun getGameInfo(
        @Path("id") id: String, @Query("key")
        apiKey: String = API_KEY
    ): Call<GameDetailsDTO>

    @GET("games/{id}/screenshots?key=${API_KEY}")
    fun getGameScreenshots(@Path("id") id: String): Call<GameScreenshotsDTO>

    /**
     *Add date interval as parameter dateInterval = (date1,date2)
     *@param dateInterval as YYYY-MM-DD,YYYY-MM-DD
     *@param orderAscendant "" to get ascendant order, "-" to get descendant order
     *@param orderBy order search by released, metacritic etc...
     * */
    @GET("games")
    fun gameReleasesFromDateToDate(
        @Query("dates") dateInterval: String,
        @Query("ordering") orderBy: String,
        @Query("key") apiKey: String = API_KEY
    ): Call<RawgGamesDTO>
}