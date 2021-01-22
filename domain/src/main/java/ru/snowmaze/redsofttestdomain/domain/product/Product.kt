package ru.snowmaze.redsofttestdomain.domain.product

import ru.snowmaze.redsofttestdomain.domain.category.Category
import java.io.Serializable

class Product(
    val id: Int,
    val title: String,
    val shortDescription: String,
    val imageUrl: String,
    val amount: Int,
    val price: Double,
    val producer: String,
    val categories: List<Category>,
    var countInCart: Int
): Serializable {

    override fun hashCode() = id

    override fun equals(other: Any?) = if(other is Product) id == other.id else false

}