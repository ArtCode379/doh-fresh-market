package dohdohs.grocery.dohfreshmarket.ui.composable.screen.checkout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CheckoutDialog(orderNumber: String, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onConfirm,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("View orders")
            }
        },
        title = { Text("Order #$orderNumber is reserved") },
        text = { Text("Your items are waiting at Doh Fresh Market. Please collect them within 24 hours.") },
    )
}
