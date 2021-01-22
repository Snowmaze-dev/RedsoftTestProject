package ru.snowmaze.redsofttestproject.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val jsonInstance = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T> T.toJson() = jsonInstance.encodeToString(this)

inline fun <reified T> String.decodeJson() = jsonInstance.decodeFromString<T>(this)