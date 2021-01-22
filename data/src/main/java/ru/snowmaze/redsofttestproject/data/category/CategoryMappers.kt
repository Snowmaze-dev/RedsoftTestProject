package ru.snowmaze.redsofttestproject.data.category

import ru.snowmaze.redsofttestdomain.domain.category.Category

internal fun Category.toEntity() = CategoryEntity(id, title, parentId)

internal fun CategoryEntity.toDomainModel() = Category(id, title, parentId)