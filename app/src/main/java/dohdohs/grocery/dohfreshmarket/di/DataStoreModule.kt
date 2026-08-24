package dohdohs.grocery.dohfreshmarket.di

import dohdohs.grocery.dohfreshmarket.data.datastore.HUFFNOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { HUFFNOnboardingPrefs(androidContext()) }
}