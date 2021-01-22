package ru.snowmaze.redsofttestproject.data.product.persistance

import androidx.room.*
import ru.snowmaze.redsofttestproject.data.product.ProductEntity

@Dao
internal interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productEntity: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    suspend fun getProducts(): List<ProductEntity>

}