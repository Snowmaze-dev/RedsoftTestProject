package ru.snowmaze.redsofttestproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestdomain.domain.product.ProductRepository

abstract class ProductViewModel(protected val productsRepository: ProductRepository): ViewModel() {

    fun onRemoveProductCart(product: Product) {
        product.countInCart--
        onProductChanged(product)
    }

    fun onAddProductCart(product: Product) {
        product.countInCart++
        onProductChanged(product)
    }

    private fun onProductChanged(product: Product) {
        viewModelScope.launch {
            productsRepository.updateProduct(product)
        }
    }

}