package ru.snowmaze.redsofttestproject.data

import androidx.room.TypeConverter
import ru.snowmaze.redsofttestproject.data.category.CategoryEntity

internal class CategoryConverter {

    @TypeConverter
    fun listToJson(list: List<CategoryEntity>) = list.toJson()

    @TypeConverter
    fun jsonToList(json: String): List<CategoryEntity> = json.decodeJson()

}