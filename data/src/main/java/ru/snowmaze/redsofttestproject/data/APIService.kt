package ru.snowmaze.redsofttestproject.data

import retrofit2.http.*
import ru.snowmaze.redsofttestproject.data.product.ProductEntity

internal interface APIService {

    @GET("products")
    suspend fun getProducts(
        @Query("filter[title]") filterTitle: String?,
        @Query("startFrom") startFrom: Int,
        @Query("maxItems") count: Int
    ): ResponseWrapper<List<ProductEntity>>

}