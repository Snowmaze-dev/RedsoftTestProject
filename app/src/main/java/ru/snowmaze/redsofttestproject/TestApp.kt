package ru.snowmaze.redsofttestproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.snowmaze.redsofttestproject.data.di.dataModule
import ru.snowmaze.redsofttestproject.ui.product.ProductViewModel
import ru.snowmaze.redsofttestproject.ui.products.ProductsViewModel

class TestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(dataModule, module {
                viewModel { ProductsViewModel(get()) }
                viewModel { ProductViewModel(get()) }
            })
        }
    }
}