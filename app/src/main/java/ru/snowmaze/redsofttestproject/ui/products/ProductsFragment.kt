package ru.snowmaze.redsofttestproject.ui.products

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialContainerTransform
import org.koin.android.viewmodel.ext.android.viewModel
import ru.snowmaze.redsofttestdomain.domain.product.Product
import ru.snowmaze.redsofttestproject.R
import ru.snowmaze.redsofttestproject.databinding.FragmentProductsBinding
import ru.snowmaze.redsofttestproject.ui.product.ProductFragment
import ru.snowmaze.redsofttestproject.ui.utils.showText

class ProductsFragment : Fragment(R.layout.fragment_products),
    ProductsAdapter.ProductsAdapterCallback {

    private val binding: FragmentProductsBinding by viewBinding()
    private val adapter = ProductsAdapter(this)
    private val viewModel: ProductsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            filterField.setText(savedInstanceState?.getString("filter", null))
            filterField.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onSearchClick()
                    true
                } else false
            }
            (listProducts.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            val products = viewModel.products.toMutableList()
            if (products.isEmpty()) {
                listProducts.adapter = adapter
                viewModel.getFirstProducts().observe(viewLifecycleOwner) { result ->
                    result.fold({
                        adapter.products = it.toMutableList()
                    }, {
                        showText(R.string.connection_failed)
                    })
                }
            } else {
                adapter.products = products
                listProducts.adapter = adapter
            }

            search.setOnClickListener {
                onSearchClick()
            }

            clearFilter.setOnClickListener {
                filterField.text = null
                onSearchClick()
            }

        }

        setFragmentResultListener("product_fragment") { key, bundle ->
            (bundle.getSerializable("product") as? Product)?.let {
                adapter.onProductChanged(it)
            }
        }
    }

    private fun onSearchClick() {
        val filter: String? = binding.filterField.text.toString().let {
            if (it.isBlank()) null else it
        }
        if(filter == viewModel.filter) return
        viewModel.loadProductsNextPage(filter)
            .observe(viewLifecycleOwner) { result ->
                result.fold({
                    adapter.products = it.toMutableList()
                    binding.listProducts.smoothScrollToPosition(0)
                }, {
                    showText(R.string.connection_failed)
                })
            }
    }

    private fun handleProductsResult(result: Result<List<Product>>) {
        result.fold({
            adapter.addProducts(it)
        }, {
        })
    }

    private fun loadNextPage() {
        viewModel.loadProductsNextPage().observe(viewLifecycleOwner) { result ->
            handleProductsResult(result)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("filter", binding.filterField.text.toString())
    }

    override fun onScrollBottom() {
        loadNextPage()
    }

    override fun onAddProductCartClick(product: Product) {
        viewModel.onAddProductCart(product)
        adapter.onProductChanged(product)
    }

    override fun onRemoveProductCartClick(product: Product) {
        viewModel.onRemoveProductCart(product)
        adapter.onProductChanged(product)
    }

    override fun onProductClick(product: Product, sharedView: View) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            val transitionName = try {
                sharedView.transitionName
            }
            catch (e: NullPointerException) {
                null
            }
            val fragment = ProductFragment.newInstance(product, transitionName).apply {
                if(transitionName != null) {
                    addSharedElement(sharedView, sharedView.transitionName)
                    sharedElementEnterTransition =
                        MaterialContainerTransform().apply {
                            scrimColor = Color.TRANSPARENT
                        }
                    sharedElementReturnTransition =
                        MaterialContainerTransform().apply {
                            scrimColor = Color.TRANSPARENT
                        }
                }
            }

            hide(this@ProductsFragment)
            add(R.id.fragments_container, fragment)
            addToBackStack(null)
        }
    }

}