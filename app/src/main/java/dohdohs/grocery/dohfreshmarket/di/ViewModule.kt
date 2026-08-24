package dohdohs.grocery.dohfreshmarket.di

import dohdohs.grocery.dohfreshmarket.ui.viewmodel.AppViewModel
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.CartViewModel
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.CheckoutViewModel
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.HUFFNOnboardingVM
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.OrderViewModel
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.ProductDetailsViewModel
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.ProductViewModel
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.HUFFNSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        HUFFNSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        HUFFNOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}