package com.example.gamergames.data.datasources.firebase.dtos

data class FieldsDTO (
    val name: StringDTO?,
    val description: StringDTO?,
    val image: StringDTO? = null,
    val genre: StringDTO?,
    val platform: StringDTO?,
    val year: StringDTO?,
    val screenshots: ScreenshotsDTO?,
    val myList: MyListDTO?,
    val playtime: IntDTO?,
    val rating: DoubleDTO?
)