package ru.snowmaze.redsofttestproject.data.product.persistance

import androidx.room.*
import ru.snowmaze.redsofttestproject.data.product.ProductInCartEntity

@Dao
internal interface ProductInCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productEntity: ProductInCartEntity)

    @Query("DELETE FROM ProductInCartEntity WHERE id LIKE :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM ProductInCartEntity")
    suspend fun getProductsInCart(): List<ProductInCartEntity>

}