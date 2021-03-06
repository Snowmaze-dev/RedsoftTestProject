package ru.snowmaze.redsofttestproject.ui.products

import androidx.lifecycle.*
import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestdomain.domain.product.ProductRepository
import ru.snowmaze.redsofttestproject.ui.ProductViewModel

class ProductsViewModel(productsRepository: ProductRepository) :
    ProductViewModel(productsRepository) {

    private val _products = mutableListOf<Product>()
    val products: List<Product> get() = _products
    private var page = 0
    private var _filter: String? = null
    val filter get() = _filter

    fun getFirstProducts(): LiveData<Result<List<Product>>> = liveData {
        if (products.isEmpty()) {
            _products.addAll(
                try {
                    productsRepository.getProducts(filter, page, false)
                } catch (e: Exception) {
                    emit(Result.failure(e))
                    productsRepository.getProducts(filter, page, true)
                }.toMutableList()
            )
        }
        emit(Result.success(_products))
        page++
    }

    fun loadProductsNextPage(
        filter: String? = this.filter
    ): LiveData<Result<List<Product>>> {
        val page = if (this.filter != filter) {
            this.page = 0
            this._filter = filter
            0
        } else this.page + 1
        return liveData {
            emit(runCatching {
                productsRepository.getProducts(filter, page, false).also {
                    if (it.isNotEmpty()) {
                        _products.addAll(it)
                        this@ProductsViewModel.page = page
                    }
                }
            })

        }
    }

}