package ru.snowmaze.redsofttestproject.data.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.snowmaze.redsofttestdomain.domain.product.ProductRepository
import ru.snowmaze.redsofttestproject.data.APIService
import ru.snowmaze.redsofttestproject.data.AppDatabase
import ru.snowmaze.redsofttestproject.data.product.ProductRepositoryImpl
import ru.snowmaze.redsofttestproject.data.product.persistance.PersistenceProductSource
import ru.snowmaze.redsofttestproject.data.product.persistance.PersistenceProductSourceImpl
import ru.snowmaze.redsofttestproject.data.product.remote.RemoteProductSource
import ru.snowmaze.redsofttestproject.data.product.remote.RemoteProductSourceImpl

val dataModule = module {
    single {
        Retrofit.Builder().baseUrl("https://rstestapi.redsoftdigital.com/api/v1/")
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory(MediaType.parse("application/json")!!)).build()
    }
    single { get<Retrofit>().create(APIService::class.java) }
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "RedsoftTestProjectDB").build()
    }
    single { get<AppDatabase>().getProductDao() }
    single { get<AppDatabase>().getProductInCartDao() }
    single<RemoteProductSource> { RemoteProductSourceImpl(get()) }
    single<PersistenceProductSource> { PersistenceProductSourceImpl(get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}