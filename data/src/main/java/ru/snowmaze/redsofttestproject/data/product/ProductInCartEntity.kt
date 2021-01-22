package ru.snowmaze.redsofttestproject.data.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal class ProductInCartEntity(@PrimaryKey val id: Int, val count: Int)