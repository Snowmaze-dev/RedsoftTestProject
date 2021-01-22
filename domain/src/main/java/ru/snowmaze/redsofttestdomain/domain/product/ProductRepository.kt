package ru.snowmaze.redsofttestdomain.domain.product

interface ProductRepository {

    suspend fun getProducts(filter: String?, page: Int, useCache: Boolean): List<Product>

    suspend fun updateProduct(product: Product)

}