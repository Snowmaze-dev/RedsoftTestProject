package ru.snowmaze.redsofttestproject.data.product

import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestdomain.domain.product.ProductRepository
import ru.snowmaze.redsofttestproject.data.product.persistance.PersistenceProductSource
import ru.snowmaze.redsofttestproject.data.product.remote.RemoteProductSource

internal class ProductRepositoryImpl(
    private val remoteSource: RemoteProductSource,
    private val persistenceProductSource: PersistenceProductSource
) : ProductRepository {

    override suspend fun getProducts(filter: String?, page: Int): List<Product> {
        val count = 15
        val productsInCart = persistenceProductSource.getProductsInCart().associateBy { it.id }
        return remoteSource.getProducts(filter, page * count, count)
            .map {
                it.toDomainModel(productsInCart[it.id]?.count ?: 0)
            }
    }

    override suspend fun updateProduct(product: Product) {
        val entity = product.toEntity()
        if (product.countInCart == 0) {
            persistenceProductSource.delete(entity)
        } else
            persistenceProductSource.insertProduct(entity)
        persistenceProductSource.insertProductInCart(
            ProductInCartEntity(
                product.id,
                product.countInCart
            )
        )
    }

}