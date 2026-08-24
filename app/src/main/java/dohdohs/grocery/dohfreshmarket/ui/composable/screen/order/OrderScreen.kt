package dohdohs.grocery.dohfreshmarket.ui.composable.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dohdohs.grocery.dohfreshmarket.data.entity.OrderEntity
import dohdohs.grocery.dohfreshmarket.ui.state.DataUiState
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.OrderViewModel
import java.time.format.DateTimeFormatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(modifier: Modifier = Modifier, viewModel: OrderViewModel = koinViewModel()) {
    val state by viewModel.ordersState.collectAsState()
    if (state is DataUiState.Populated) {
        val orders = (state as DataUiState.Populated<List<OrderEntity>>).data.sortedByDescending { it.timestamp }
        LazyColumn(modifier.fillMaxSize().padding(16.dp)) {
            items(orders, key = { it.orderNumber }) { order ->
                OrderCard(order)
            }
        }
    } else {
        Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Default.ReceiptLong, null, tint = MaterialTheme.colorScheme.primary)
            Text("No orders yet", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 12.dp))
            Text("Your collection history will appear here.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun OrderCard(order: OrderEntity) {
    Card(Modifier.fillMaxWidth().padding(bottom = 12.dp), shape = RoundedCornerShape(4.dp)) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Order #${order.orderNumber}", style = MaterialTheme.typography.titleMedium)
                Surface(color = Color(0xFFE3F3E5), shape = RoundedCornerShape(50)) {
                    Text("Completed", Modifier.padding(horizontal = 10.dp, vertical = 5.dp), color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                }
            }
            Text(order.timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(order.description, Modifier.padding(top = 10.dp), maxLines = 2)
            Text("£%.2f".format(order.price), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 12.dp))
        }
    }
}
