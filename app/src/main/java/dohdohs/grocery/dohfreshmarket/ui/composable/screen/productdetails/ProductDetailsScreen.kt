package dohdohs.grocery.dohfreshmarket.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dohdohs.grocery.dohfreshmarket.R
import dohdohs.grocery.dohfreshmarket.data.model.Product
import dohdohs.grocery.dohfreshmarket.ui.composable.shared.HUFFNContentWrapper
import dohdohs.grocery.dohfreshmarket.ui.composable.shared.HUFFNEmptyView
import dohdohs.grocery.dohfreshmarket.ui.state.DataUiState
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(productId: Int, modifier: Modifier = Modifier, viewModel: ProductDetailsViewModel = koinViewModel()) {
    val productState by viewModel.productDetailsState.collectAsState()
    LaunchedEffect(productId) { viewModel.observeProductDetails(productId) }
    HUFFNContentWrapper(
        dataState = productState,
        dataPopulated = {
            ProductDetails((productState as DataUiState.Populated).data, modifier, viewModel::addProductToCart)
        },
        dataEmpty = {
            HUFFNEmptyView(
                modifier = Modifier.fillMaxSize(),
                primaryText = stringResource(R.string.huffn_product_details_state_empty_primary_text),
            )
        },
    )
}

@Composable
private fun ProductDetails(product: Product, modifier: Modifier, addToCart: () -> Unit) {
    var cartAdded by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { 3 }
    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }
    Box(modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 96.dp)) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth().height(330.dp)) {
                AsyncImage(product.imageUrl, product.title, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
            Row(Modifier.align(Alignment.CenterHorizontally).padding(12.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(3) { index ->
                    Box(
                        Modifier.width(if (pagerState.currentPage == index) 22.dp else 7.dp).height(7.dp)
                            .background(if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, RoundedCornerShape(50)),
                    )
                }
            }
            Column(Modifier.padding(horizontal = 20.dp)) {
                Text(product.title, style = MaterialTheme.typography.headlineMedium)
                Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(50), modifier = Modifier.padding(top = 12.dp)) {
                    Text(stringResource(product.category.titleRes), Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = MaterialTheme.colorScheme.primary)
                }
                Text("About this market pick", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
                Text(product.description, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
                Text("Freshness promise", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 24.dp))
                Text("Carefully selected and held for collection in conditions that protect flavour and quality.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Surface(shadowElevation = 8.dp, modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("£%.2f".format(product.price), style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(18.dp))
                Button(
                    onClick = {
                        addToCart()
                        cartAdded = true
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Add to Cart")
                }
            }
        }
        AnimatedVisibility(
            visible = cartAdded,
            enter = slideInVertically { it },
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp),
        ) {
            Surface(tonalElevation = 6.dp, modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary)
                    Text("Added to cart", Modifier.padding(start = 10.dp), fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
