package ru.snowmaze.redsofttestproject.data.product.persistance

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.snowmaze.redsofttestproject.data.product.ProductEntity
import ru.snowmaze.redsofttestproject.data.product.ProductInCartEntity

internal class PersistenceProductSourceImpl(
    private val productDao: ProductDao,
    private val productInCartDao: ProductInCartDao
) : PersistenceProductSource {

    override suspend fun getProducts(): List<ProductEntity> = withContext(Dispatchers.IO) {
        productDao.getProducts()
    }

    override suspend fun getProductsInCart(): List<ProductInCartEntity> =
        withContext(Dispatchers.IO) {
            productInCartDao.getProductsInCart()
        }

    override suspend fun insertProduct(productEntity: ProductEntity) {
        withContext(Dispatchers.IO) {
            productDao.insert(productEntity)
        }
    }

    override suspend fun insertProductInCart(productInCartEntity: ProductInCartEntity) {
        withContext(Dispatchers.IO) {
            productInCartDao.insert(productInCartEntity)
        }
    }

    override suspend fun delete(productEntity: ProductEntity) {
        withContext(Dispatchers.IO) {
            productDao.delete(productEntity)
            productInCartDao.delete(productEntity.id)
        }
    }

}