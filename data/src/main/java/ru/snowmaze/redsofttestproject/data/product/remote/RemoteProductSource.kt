package ru.snowmaze.redsofttestproject.data.product.remote

import ru.snowmaze.redsofttestproject.data.product.ProductEntity
import ru.snowmaze.redsofttestproject.data.product.ProductSource

internal interface RemoteProductSource: ProductSource {

    suspend fun getProducts(filter: String?, startFrom: Int, count: Int): List<ProductEntity>

}