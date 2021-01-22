package ru.snowmaze.redsofttestproject.data.product

import ru.snowmaze.redsofttestdomain.domain.category.Category
import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestproject.data.category.CategoryEntity
import ru.snowmaze.redsofttestproject.data.category.toDomainModel
import ru.snowmaze.redsofttestproject.data.category.toEntity

internal fun Product.toEntity() = ProductEntity(
    id,
    title,
    shortDescription,
    imageUrl,
    amount,
    price,
    producer,
    categories.map(Category::toEntity)
)


internal fun ProductEntity.toDomainModel(countInCart: Int) = Product(
    id,
    title,
    shortDescription,
    imageUrl,
    amount,
    price,
    producer,
    categories.map(CategoryEntity::toDomainModel),
    countInCart
)