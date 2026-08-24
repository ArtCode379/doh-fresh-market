package dohdohs.grocery.dohfreshmarket.data.repository

import dohdohs.grocery.dohfreshmarket.data.datastore.HUFFNOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HUFFNOnboardingRepo(
    private val huffnOnboardingStoreManager: HUFFNOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return huffnOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            huffnOnboardingStoreManager.setOnboardedState(state)
        }
    }
}