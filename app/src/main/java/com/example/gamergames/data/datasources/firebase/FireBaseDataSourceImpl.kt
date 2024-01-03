package com.example.gamergames.data.datasources.firebase

import android.util.Log
import com.example.gamergames.AsyncResult
import com.example.gamergames.data.datasources.firebase.mappers.toBO
import com.example.gamergames.data.datasources.firebase.mappers.toPostGameDTO
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams
import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**Data Source implementation where we receive all the data from Firebase and is converted to BO items*/
class FireBaseDataSourceImpl : FirebaseDataSource {

    /**Retrofit builder*/
    companion object {
        private val httpLoggingInterceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val okHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://firestore.googleapis.com/v1/projects/gamer-games/databases/(default)/documents/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val remoteFirebase = retrofit.create(FirebaseWs::class.java)


    override suspend fun getGamesList(userId: String): AsyncResult<List<GameBO>> {
        return try {

            val response = remoteFirebase.getGamesList(userId).execute()
            val data = response.body().toBO()
            if (response.errorBody() == null) {
                return AsyncResult.success(data)
            } else {

                return AsyncResult.error(
                    "Error: " + response.code().toString(),
                    data
                )
            }
        } catch (exception: Exception) {
            Log.i("HTTP Exception: ", exception.toString())
            AsyncResult.exception(exception)
        }
    }

    override suspend fun addNewGame(
        userId: String,
        params: AddGameUseCaseParams
    ): AsyncResult<Boolean> {
        return try {

            val dtoToSend = params.toPostGameDTO()
            val call = remoteFirebase.addNewGame(userId, dtoToSend)
            call.execute()

            if (call.execute().errorBody() == null) {
                return AsyncResult.success(true)
            } else {
                return AsyncResult.error(
                    "Error: " + call.execute().code().toString(), false
                )
            }
        } catch (exception: Exception) {
            AsyncResult.exception(exception, false)
        }
    }

    override suspend fun deleteGame(userId: String, gameId: String): AsyncResult<Boolean> {
        return try {

            val call = remoteFirebase.deleteGame(userId, gameId)
            call.execute()

            if (call.execute().errorBody() == null) {
                return AsyncResult.success(true)
            } else {
                return AsyncResult.error(
                    "Error: " + call.execute().code().toString(), false
                )
            }
        } catch (exception: Exception) {
            AsyncResult.exception(exception, false)
        }
    }

    override suspend fun updateLikedGameList(
        userId: String,
        id: String,
        data: AddToLikedGames
    ): AsyncResult<Boolean> {
        return try {

            val dtoToSend = data.toPostGameDTO()
            val call = remoteFirebase.updateFavoriteGame(userId, id, dtoToSend)
            call.execute()
            if (call.execute().errorBody() == null) {
                return AsyncResult.success(true)
            } else {
                return AsyncResult.error(
                    "Error: " + call.execute().code().toString(), false
                )
            }
        } catch (exception: Exception) {
            AsyncResult.exception(exception, false)
        }
    }

}