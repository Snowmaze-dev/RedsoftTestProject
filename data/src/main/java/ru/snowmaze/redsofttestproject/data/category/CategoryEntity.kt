package ru.snowmaze.redsofttestproject.data.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CategoryEntity(val id: Int, val title: String, @SerialName("parent_id") val parentId: Int?)