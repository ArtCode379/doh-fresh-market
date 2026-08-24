package dohdohs.grocery.dohfreshmarket.ui.composable.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dohdohs.grocery.dohfreshmarket.R
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.HUFFNSplashVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: HUFFNSplashVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboarded by viewModel.onboardedState.collectAsStateWithLifecycle()
    val alpha = androidx.compose.runtime.remember { Animatable(0f) }
    val offset = androidx.compose.runtime.remember { Animatable(28f) }
    LaunchedEffect(Unit) {
        joinAll(
            launch { alpha.animateTo(1f, tween(500)) },
            launch { offset.animateTo(0f, tween(500)) },
        )
        delay(1000)
        if (onboarded) onNavigateToHomeScreen() else onNavigateToOnboarding()
    }
    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Image(
            painter = painterResource(R.drawable.onboarding_fresh),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(440.dp),
            contentScale = ContentScale.Crop,
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.alpha(alpha.value).padding(bottom = offset.value.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(painterResource(R.drawable.icon), null, Modifier.size(88.dp).scale(0.9f + alpha.value * 0.1f))
                Text("Doh Fresh Market", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(top = 16.dp))
                Text("Freshly chosen. Ready for you.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
