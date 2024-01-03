package com.example.gamergames.data.datasources.rawg.mappers

import android.text.Html
import com.example.gamergames.data.datasources.rawg.dtos.gamescreenshots.GameScreenshotsDTO
import com.example.gamergames.data.datasources.rawg.dtos.gamescreenshots.ImageDTO
import com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos.GameDetailsDTO
import com.example.gamergames.data.datasources.rawg.dtos.rawggamedetailsdtos.TagsDTO
import com.example.gamergames.data.datasources.rawg.dtos.rawggamesdtos.RawgGamesDTO
import com.example.gamergames.data.datasources.rawg.dtos.rawggamesdtos.ResultDTO
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO
import com.example.gamergames.data.model.rawg.bos.ImageBO
import com.example.gamergames.data.model.rawg.bos.TagBO

/**Mapper to get BO from received DTO*/
private fun ResultDTO.toBo(): AddGameBo {
    return AddGameBo(
        name ?: "",
        released ?: "",
        backgroundImage ?: "",
        id ?: ""
    )
}

    //region Public methods
fun RawgGamesDTO?.toBo(): List<AddGameBo> {
    return this?.results?.map { resultDTO ->
        resultDTO.toBo()
    } ?: listOf()
}

fun GameDetailsDTO?.toRawgGamesDetailsBO(): GameDetailsBO {
    return GameDetailsBO(
        this?.id ?: "",
        this?.name ?: "",
        this?.description?.let { text -> Html.fromHtml(text, 0) }.toString(),
        this?.released ?: "",
        this?.tags?.mapNotNull { item -> item.toBo() } ?: listOf(),
        this?.backgroundImage ?: "",
        this?.platforms?.mapNotNull { it.platform?.name } ?: listOf(),
        this?.playtime ?: 9,
        this?.metacritic ?: 9,
        this?.genres?.mapNotNull { it?.name } ?: listOf()
    )
}

fun GameScreenshotsDTO?.toGameScreenshotsBO(): GameScreenshotsBO {
    return GameScreenshotsBO(this?.results?.mapNotNull { item -> item.toBO() } ?: listOf())
}


fun ImageDTO?.toBO(): ImageBO? {
    return this?.let { dto ->
        ImageBO(
            dto.image ?: ""
        )
    }
}

fun TagsDTO?.toBo(): TagBO? {
    return this?.let { dto ->
        TagBO(
            dto.id ?: -1,
            dto.name ?: "",
            dto.slug ?: "",
            dto.language ?: "",
            dto.gamesCount ?: 0,
            dto.imageBackground ?: ""
        )
    }
}
//endregion
