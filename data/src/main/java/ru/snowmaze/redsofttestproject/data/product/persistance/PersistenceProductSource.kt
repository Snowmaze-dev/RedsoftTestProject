package ru.snowmaze.redsofttestproject.data.product.persistance

import ru.snowmaze.redsofttestproject.data.product.ProductInCartEntity
import ru.snowmaze.redsofttestproject.data.product.ProductEntity
import ru.snowmaze.redsofttestproject.data.product.ProductSource

internal interface PersistenceProductSource: ProductSource {

    suspend fun getProducts(): List<ProductEntity>

    suspend fun getProductsInCart(): List<ProductInCartEntity>

    suspend fun insertProduct(productEntity: ProductEntity)

    suspend fun insertProductInCart(productInCartEntity: ProductInCartEntity)

    suspend fun delete(productEntity: ProductEntity)

}