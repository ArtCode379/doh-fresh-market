package dohdohs.grocery.dohfreshmarket.ui.composable.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dohdohs.grocery.dohfreshmarket.R
import dohdohs.grocery.dohfreshmarket.data.model.Product
import dohdohs.grocery.dohfreshmarket.data.model.ProductCategory
import dohdohs.grocery.dohfreshmarket.ui.composable.shared.HUFFNContentWrapper
import dohdohs.grocery.dohfreshmarket.ui.composable.shared.HUFFNEmptyView
import dohdohs.grocery.dohfreshmarket.ui.state.DataUiState
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()
    HUFFNContentWrapper(
        dataState = productsState,
        dataPopulated = {
            MarketContent((productsState as DataUiState.Populated).data, modifier, onNavigateToProductDetails)
        },
        dataEmpty = {
            HUFFNEmptyView(
                modifier = Modifier.fillMaxSize(),
                primaryText = stringResource(R.string.huffn_products_state_empty_primary_text),
            )
        },
    )
}

@Composable
private fun MarketContent(products: List<Product>, modifier: Modifier, onProduct: (Int) -> Unit) {
    var category by remember { mutableStateOf<ProductCategory?>(null) }
    val visible = if (category == null) products.drop(1) else products.filter { it.category == category }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
            Column {
                Text("Good food, freshly found", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(20.dp))
                FeaturedProduct(products.first(), onProduct)
                Text("Shop by category", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(20.dp, 24.dp, 20.dp, 10.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        CategoryButton("All", category == null) { category = null }
                    }
                    items(ProductCategory.entries) { item ->
                        CategoryButton(stringResource(item.titleRes), category == item) { category = item }
                    }
                }
                Text("Market picks", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(20.dp, 24.dp, 20.dp, 12.dp))
            }
        }
        items(visible, key = { it.id }) { product ->
            ProductCard(product, onProduct, Modifier.padding(start = if (product.id % 2 == 0) 0.dp else 12.dp, end = 0.dp))
        }
    }
}

@Composable
private fun FeaturedProduct(product: Product, onProduct: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(220.dp).clickable { onProduct(product.id) },
        shape = RoundedCornerShape(4.dp),
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(product.imageUrl, product.title, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)))))
            Column(Modifier.align(Alignment.BottomStart).padding(20.dp)) {
                Text("TODAY'S FAVOURITE", color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.labelLarge)
                Text(product.title, color = Color.White, style = MaterialTheme.typography.headlineMedium)
                Text("£%.2f".format(product.price), color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CategoryButton(label: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(50),
    ) {
        Text(label, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ProductCard(product: Product, onProduct: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(bottom = 12.dp).fillMaxWidth().height(if (product.id % 2 == 0) 240.dp else 190.dp)
            .clickable { onProduct(product.id) },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        AsyncImage(product.imageUrl, product.title, Modifier.fillMaxWidth().weight(1f), contentScale = ContentScale.Crop)
        Column(Modifier.padding(10.dp)) {
            Text(product.title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("£%.2f".format(product.price), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text(stringResource(product.category.titleRes), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
