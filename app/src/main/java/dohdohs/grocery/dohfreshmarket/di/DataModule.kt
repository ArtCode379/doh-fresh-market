package dohdohs.grocery.dohfreshmarket.di

import dohdohs.grocery.dohfreshmarket.data.repository.CartRepository
import dohdohs.grocery.dohfreshmarket.data.repository.HUFFNOnboardingRepo
import dohdohs.grocery.dohfreshmarket.data.repository.OrderRepository
import dohdohs.grocery.dohfreshmarket.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        HUFFNOnboardingRepo(
            huffnOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}