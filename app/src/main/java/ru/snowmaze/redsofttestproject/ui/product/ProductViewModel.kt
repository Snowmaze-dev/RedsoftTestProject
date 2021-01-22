package ru.snowmaze.redsofttestproject.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestdomain.domain.product.ProductRepository

class ProductViewModel(private val productsRepository: ProductRepository): ViewModel() {

    fun onProductChanged(product: Product) {
        viewModelScope.launch {
            productsRepository.updateProduct(product)
        }
    }

}