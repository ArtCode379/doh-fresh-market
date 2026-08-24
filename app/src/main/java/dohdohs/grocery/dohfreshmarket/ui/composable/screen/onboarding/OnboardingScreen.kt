package dohdohs.grocery.dohfreshmarket.ui.composable.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dohdohs.grocery.dohfreshmarket.R
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.HUFFNOnboardingVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private data class Page(val title: String, val description: String, val image: Int)
private val pages = listOf(
    Page("Market-fresh every day", "Discover colourful produce selected for flavour, quality, and the season.", R.drawable.onboarding_fresh),
    Page("Baked with care", "Reserve artisan bread, flaky pastries, refreshing drinks, and small-batch treats.", R.drawable.onboarding_bakery),
    Page("Ready for collection", "Place your reservation in moments. We will hold it in store for the next 24 hours.", R.drawable.onboarding_pickup),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: HUFFNOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val completed by viewModel.onboardingSetState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    LaunchedEffect(completed) {
        if (completed) onNavigateToHomeScreen()
    }
    Column(modifier = modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            val page = pages[index]
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(page.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).height(520.dp),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.94f)).padding(28.dp),
                ) {
                    Text(page.title, style = MaterialTheme.typography.headlineLarge)
                    Text(page.description, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 12.dp))
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pages.indices.forEach { index ->
                    Box(
                        Modifier.size(if (index == pagerState.currentPage) 10.dp else 7.dp).clip(CircleShape)
                            .background(if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
                    )
                }
            }
            Button(
                onClick = {
                    if (pagerState.currentPage == pages.lastIndex) viewModel.setOnboarded()
                    else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
            ) {
                Text(if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Next")
            }
        }
    }
}
