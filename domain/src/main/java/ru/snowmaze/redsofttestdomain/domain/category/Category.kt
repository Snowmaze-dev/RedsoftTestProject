package ru.snowmaze.redsofttestdomain.domain.category

import java.io.Serializable

class Category(val id: Int, val title: String, val parentId: Int?): Serializable