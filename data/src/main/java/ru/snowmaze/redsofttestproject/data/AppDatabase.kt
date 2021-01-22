package ru.snowmaze.redsofttestproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.snowmaze.redsofttestproject.data.product.ProductEntity
import ru.snowmaze.redsofttestproject.data.product.ProductInCartEntity
import ru.snowmaze.redsofttestproject.data.product.persistance.ProductDao
import ru.snowmaze.redsofttestproject.data.product.persistance.ProductInCartDao

@TypeConverters(CategoryConverter::class)
@Database(
    entities = [ProductEntity::class, ProductInCartEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    abstract fun getProductInCartDao(): ProductInCartDao

}