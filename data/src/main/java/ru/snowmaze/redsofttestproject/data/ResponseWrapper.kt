package ru.snowmaze.redsofttestproject.data

import kotlinx.serialization.Serializable

@Serializable
class ResponseWrapper<T>(val data: T)