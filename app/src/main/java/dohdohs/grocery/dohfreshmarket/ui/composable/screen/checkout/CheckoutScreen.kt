package dohdohs.grocery.dohfreshmarket.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dohdohs.grocery.dohfreshmarket.data.entity.OrderEntity
import dohdohs.grocery.dohfreshmarket.ui.state.DataUiState
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit,
) {
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalid by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    if (orderState is DataUiState.Populated) {
        CheckoutDialog((orderState as DataUiState.Populated<OrderEntity>).data.orderNumber, onNavigateToOrdersScreen)
    }
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Text("Reserve your market order", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Tell us who is collecting. We will keep your order ready in store for 24 hours.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 20.dp),
        )
        CheckoutField(viewModel.customerFirstName, viewModel::updateCustomerFirstName, "First name")
        CheckoutField(viewModel.customerLastName, viewModel::updateCustomerLastName, "Last name")
        CheckoutField(
            viewModel.customerEmail,
            viewModel::updateCustomerEmail,
            "Email",
            emailInvalid,
            KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Card(Modifier.fillMaxWidth().padding(top = 24.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Collection summary", style = MaterialTheme.typography.titleLarge)
                Text("Your full basket will be reserved after confirmation.", modifier = Modifier.padding(top = 8.dp))
                Text("Collection window: 24 hours", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 8.dp))
            }
        }
        Button(
            onClick = viewModel::placeOrder,
            enabled = viewModel.customerFirstName.isNotBlank() && viewModel.customerLastName.isNotBlank() && viewModel.customerEmail.isNotBlank(),
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        ) {
            Text("Place Order")
        }
    }
}

@Composable
private fun CheckoutField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = isError,
        supportingText = if (isError) ({ Text("Enter a valid email address") }) else null,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
    )
}
