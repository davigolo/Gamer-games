package com.example.gamergames.data.datasources.firebase.mappers

import android.net.Uri
import com.example.gamergames.AsyncResult
import com.example.gamergames.data.datasources.firebase.dtos.ArrayValueDTO
import com.example.gamergames.data.datasources.firebase.dtos.DocumentDTO
import com.example.gamergames.data.datasources.firebase.dtos.DoubleDTO
import com.example.gamergames.data.datasources.firebase.dtos.FieldsDTO
import com.example.gamergames.data.datasources.firebase.dtos.GameDTO
import com.example.gamergames.data.datasources.firebase.dtos.IntDTO
import com.example.gamergames.data.datasources.firebase.dtos.MyListDTO
import com.example.gamergames.data.datasources.firebase.dtos.PostGameDTO
import com.example.gamergames.data.datasources.firebase.dtos.ScreenshotsDTO
import com.example.gamergames.data.datasources.firebase.dtos.StringDTO
import com.example.gamergames.data.datasources.firebase.dtos.ValuesDTO
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.data.model.firebase.bos.MyListBO
import com.example.gamergames.data.model.firebase.bos.ScreenshotsBO
import com.example.gamergames.domain.usecases.firebase.params.AddGameUseCaseParams
import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames

/**Mapper to get BO from received DTO*/
//region PUBLIC METHODS
fun GameDTO?.toBO(): List<GameBO> {
    return this?.documents?.mapNotNull { documentDTO ->
        documentDTO?.let { dto ->
            GameBO(
                title = dto.getTitleGame(),
                description = dto.getTitleDescription(),
                image = dto.getGameImage(),
                genre = dto.getGameGenre(),
                platform = dto.getGamePlatform(),
                gameId = dto.getGameId(),
                gameYear = dto.getGameYear(),
                screenshots = dto.getGameScreenshots(),
                myList = dto.getLikedGame(),
                playtime = dto.getPlaytime(),
                rating = dto.getRating()
            )
        }
    } ?: listOf()
}

fun AddToLikedGames.toPostGameDTO(): PostGameDTO {
    return PostGameDTO(
        FieldsDTO(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
                MyListDTO(myList.booleanValue),
            null,
            null,
        )
    )
}

fun AddGameUseCaseParams.toPostGameDTO(): PostGameDTO {
    return PostGameDTO(
        FieldsDTO(
            StringDTO(title),
            StringDTO(description),
            StringDTO(image),
            StringDTO(genre),
            StringDTO(platform),
            StringDTO(year),
            ScreenshotsDTO(
                ArrayValueDTO(
                    screenshots.map { value -> ValuesDTO(value) }
                )
            ),
            MyListDTO(myList),
            IntDTO(playtime),
            DoubleDTO(rating)
        )
    )
}

//endregion PUBLIC METHODS

//region PRIVATE METHODS
private fun DocumentDTO.getTitleGame(): String {
    return this.fields?.name?.stringValue ?: ""
}

private fun DocumentDTO.getTitleDescription(): String {
    return this.fields?.description?.stringValue ?: ""
}

private fun DocumentDTO.getGameImage(): String {
    return this.fields?.image?.stringValue ?: ""
}

private fun DocumentDTO.getGameGenre(): String {
    return this.fields?.genre?.stringValue ?: ""
}

private fun DocumentDTO.getGamePlatform(): String {
    return this.fields?.platform?.stringValue ?: ""
}

private fun DocumentDTO.getGameId(): String {
    return this.name.let { path -> Uri.parse(path).lastPathSegment } ?: ""
}

private fun DocumentDTO.getGameYear(): String {
    return this.fields?.year?.stringValue ?: ""
}

private fun DocumentDTO.getGameScreenshots(): ScreenshotsBO {
    return ScreenshotsBO(this.fields?.screenshots?.arrayValue?.values?.map { it.stringValue }
        ?: listOf())
}

private fun DocumentDTO.getLikedGame(): MyListBO {
    val isGameLiked = this.fields?.myList?.booleanValue ?: false
    return MyListBO(isGameLiked)
}

private fun DocumentDTO.getRating(): Double {
    return this.fields?.rating?.doubleValue ?: 0.0
}

private fun DocumentDTO.getPlaytime(): Int {
    return this.fields?.playtime?.integerValue ?: 9
}
//endregion PRIVATE METHODS
