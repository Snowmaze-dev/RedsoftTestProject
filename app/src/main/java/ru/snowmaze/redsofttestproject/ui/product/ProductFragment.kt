package ru.snowmaze.redsofttestproject.ui.product

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestproject.R
import ru.snowmaze.redsofttestproject.databinding.CartViewBinding
import ru.snowmaze.redsofttestproject.databinding.FragmentProductBinding
import ru.snowmaze.redsofttestproject.ui.utils.dpToPx

class ProductFragment : Fragment(R.layout.fragment_product) {

    private val binding: FragmentProductBinding by viewBinding()
    private val viewModel: ProductViewModel by viewModel()
    private var previousInCart = 0
    private var cartBinding: CartViewBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        val product = requireArguments().getSerializable("product") as Product
        val transitionName: String? = requireArguments().getString("transition_name")
        previousInCart = product.countInCart
        with(binding) {
            lifecycleScope.launch {
                photo.load(product.imageUrl, builder = {
                    val dp = requireContext().dpToPx(200).toInt()
                    size(dp).scale(Scale.FIT)
                    listener(onError = { imageRequest: ImageRequest, throwable: Throwable ->
                        photo.updateLayoutParams {
                            val photoDp = requireContext().dpToPx(150).toInt()
                            width = photoDp
                            height = photoDp
                        }
                    })
                }).await()
                startPostponedEnterTransition()
            }
            toolbar.title = product.title
            title.text = product.title
            producer.text = product.producer
            photo.transitionName = transitionName
            price.text =
                getString(R.string.product_price, product.price.toString().replace(".", ","))
            description.text = product.shortDescription
            onCartCountChanged(product)
            categories.text = buildString {
                product.categories.forEach {
                    append(it.title + System.lineSeparator())
                }
            }

            toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun onCartCountChanged(product: Product) {
        setFragmentResult("product_fragment", Bundle().apply {
            putSerializable("product", product)
        })
        viewModel.onProductChanged(product)
        if (product.countInCart == 0) {
            if (binding.cartContainer.isNotEmpty()) {
                cartBinding = null
                binding.cartContainer.removeAllViews()
            }
            val view = layoutInflater.inflate(R.layout.add_to_cart, null)
            binding.cartContainer.addView(view)
            view.setOnClickListener {
                previousInCart = product.countInCart
                product.countInCart++
                onCartCountChanged(product)
            }
        } else {
            if (previousInCart == 0 || binding.cartContainer.isEmpty()) {
                binding.cartContainer.removeAllViews()
                createCartBinding(product)
            }
            cartBinding!!.cartCount.text =
                getString(R.string.product_cart_count, product.countInCart.toString())
        }
    }

    private fun createCartBinding(product: Product) {
        val cartBinding =
            CartViewBinding.inflate(layoutInflater, binding.cartContainer, true)
        cartBinding.cartCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        this.cartBinding = cartBinding
        with(cartBinding) {
            cartAdd.setOnClickListener {
                previousInCart = product.countInCart
                product.countInCart++
                onCartCountChanged(product)
            }
            cartRemove.setOnClickListener {
                previousInCart = product.countInCart
                product.countInCart--
                onCartCountChanged(product)
            }
        }
    }

    companion object {

        fun newInstance(product: Product, transitionName: String?) = ProductFragment().apply {
            arguments = Bundle().apply {
                putSerializable("product", product)
                putString("transition_name", transitionName)
            }
        }

    }

}