package ru.snowmaze.redsofttestproject.data.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.snowmaze.redsofttestproject.data.category.CategoryEntity

@Serializable
@Entity
internal class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    @SerialName("short_description") val shortDescription: String,
    @SerialName("image_url") val imageUrl: String,
    val amount: Int,
    val price: Double,
    val producer: String,
    val categories: List<CategoryEntity>
)