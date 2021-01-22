package ru.snowmaze.redsofttestproject.ui.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestproject.R
import ru.snowmaze.redsofttestproject.databinding.AddCartViewBinding
import ru.snowmaze.redsofttestproject.databinding.CardProductBinding
import ru.snowmaze.redsofttestproject.databinding.CartViewBinding
import ru.snowmaze.redsofttestproject.ui.utils.animatePlaceholder

class ProductsAdapter(private val callback: ProductsAdapterCallback) :
    RecyclerView.Adapter<ProductsAdapter.ProductVH>() {

    var products = mutableListOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addProducts(products: List<Product>) {
        val start = this.products.size
        this.products.addAll(products)
        notifyItemRangeChanged(start, this.products.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val inflater = LayoutInflater.from(parent.context)
        val isCartEmpty = viewType == 0
        return ProductVH(
            inflater.inflate(
                R.layout.card_product, parent, false
            ).apply {
                inflater.inflate(
                    if (isCartEmpty) R.layout.add_cart_view else R.layout.cart_view,
                    findViewById(R.id.cart_container)
                )
            }, isCartEmpty, callback
        )
    }

    override fun getItemViewType(position: Int) = if (products[position].countInCart == 0) 0 else 1

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(products[position])
        if (itemCount - 2 == position) callback.onScrollBottom()
    }

    override fun getItemCount() = products.size

    fun onProductChanged(product: Product) {
        notifyItemChanged(products.indexOf(product), Unit)
    }

    class ProductVH(
        itemView: View,
        isCartEmpty: Boolean,
        private val callback: ProductsAdapterCallback
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding: CardProductBinding by viewBinding()
        private val cartBinding: ViewBinding

        init {
            val cartView = binding.cartContainer[0]
            cartBinding =
                if (isCartEmpty) AddCartViewBinding.bind(cartView)
                else CartViewBinding.bind(cartView)
        }

        fun bind(product: Product) {
            with(binding) {
                val context = root.context
                product.categories.firstOrNull()?.let {
                    category.text = it.title
                }
                name.text = product.title
                producer.text = product.producer
                photo.load(product.imageUrl, builder = {
                    crossfade(true).animatePlaceholder(context)
                    listener(onSuccess = { imageRequest: ImageRequest, metadata: ImageResult.Metadata ->
                        photo.transitionName = product.imageUrl
                    })
                })
                price.text = context.getString(
                    R.string.product_price,
                    product.price.toString().replace(".", ",")
                )
                root.setOnClickListener {
                    callback.onProductClick(product, photo)
                }
                with(cartBinding) {
                    if (this is AddCartViewBinding) {
                        root.setOnClickListener {
                            callback.onAddProductCartClick(product)
                        }
                    }
                    if (this is CartViewBinding) {
                        cartAdd.setOnClickListener {
                            callback.onAddProductCartClick(product)
                        }
                        cartRemove.setOnClickListener {
                            callback.onRemoveProductCartClick(product)
                        }
                        cartCount.text = context.getString(
                            R.string.product_cart_count,
                            product.countInCart.toString()
                        )
                    }
                }
            }
        }

    }

    interface ProductsAdapterCallback {

        fun onScrollBottom()

        fun onAddProductCartClick(product: Product)

        fun onRemoveProductCartClick(product: Product)

        fun onProductClick(product: Product, sharedView: View)

    }

}