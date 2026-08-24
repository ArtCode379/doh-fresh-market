package dohdohs.grocery.dohfreshmarket.ui.composable.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dohdohs.grocery.dohfreshmarket.ui.state.CartItemUiState
import dohdohs.grocery.dohfreshmarket.ui.state.DataUiState
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val state by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val total by viewModel.totalPrice.collectAsStateWithLifecycle()
    when (state) {
        is DataUiState.Populated -> CartList(
            (state as DataUiState.Populated).data,
            total,
            modifier,
            viewModel::incrementProductInCart,
            viewModel::decrementItemInCart,
            viewModel::deleteFromCart,
            onNavigateToCheckoutScreen,
        )
        else -> EmptyCart(modifier)
    }
}

@Composable
private fun EmptyCart(modifier: Modifier) {
    Column(
        modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(Icons.Default.RemoveShoppingCart, null, Modifier.size(76.dp), tint = MaterialTheme.colorScheme.primary)
        Text("Your basket is waiting", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 20.dp))
        Text("Start shopping to reserve something fresh.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun CartList(
    items: List<CartItemUiState>,
    total: Double,
    modifier: Modifier,
    plus: (Int) -> Unit,
    minus: (Int) -> Unit,
    remove: (Int) -> Unit,
    checkout: () -> Unit,
) {
    Column(modifier.fillMaxSize()) {
        LazyColumn(Modifier.weight(1f), contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)) {
            items(items, key = { it.productId }) { item ->
                Card(Modifier.fillMaxWidth().padding(bottom = 12.dp), shape = RoundedCornerShape(4.dp)) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(item.productImageUrl, item.productTitle, Modifier.size(72.dp), contentScale = ContentScale.Crop)
                        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                            Text(item.productTitle, style = MaterialTheme.typography.titleMedium)
                            Text("£%.2f".format(item.productPrice), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedButton(onClick = { if (item.quantity == 1) remove(item.productId) else minus(item.productId) }) {
                                    Text("−")
                                }
                                Text(item.quantity.toString(), Modifier.padding(horizontal = 14.dp))
                                OutlinedButton(onClick = { plus(item.productId) }) {
                                    Text("+")
                                }
                            }
                        }
                        IconButton(onClick = { remove(item.productId) }) {
                            Icon(Icons.Default.Delete, "Remove")
                        }
                    }
                }
            }
        }
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal")
                Text("£%.2f".format(total))
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", style = MaterialTheme.typography.titleLarge)
                Text("£%.2f".format(total), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            }
            Button(onClick = checkout, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Text("Proceed to Checkout")
            }
        }
    }
}
