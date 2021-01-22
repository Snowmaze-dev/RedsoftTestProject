package ru.snowmaze.redsofttestproject.data.product.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.snowmaze.redsofttestproject.data.APIService
import ru.snowmaze.redsofttestproject.data.product.ProductEntity

internal class RemoteProductSourceImpl(private val apiService: APIService) : RemoteProductSource {

    override suspend fun getProducts(
        filter: String?,
        startFrom: Int,
        count: Int
    ): List<ProductEntity> = withContext(Dispatchers.IO) {
        apiService.getProducts(filter, startFrom, count).data
    }


}