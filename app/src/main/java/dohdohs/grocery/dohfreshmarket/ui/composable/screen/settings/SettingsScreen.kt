package dohdohs.grocery.dohfreshmarket.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val supportUrl = "https://dohdohs.casa"
    Column(modifier.fillMaxSize().padding(20.dp)) {
        Text("About", style = MaterialTheme.typography.headlineMedium)
        Card(Modifier.fillMaxWidth().padding(top = 14.dp)) {
            Column(Modifier.padding(18.dp)) {
                Icon(Icons.Default.Storefront, null, tint = MaterialTheme.colorScheme.primary)
                Text("Doh Fresh Market", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 12.dp))
                Text("DOH-DOHS LTD", color = MaterialTheme.colorScheme.onSurfaceVariant)
                HorizontalDivider(Modifier.padding(vertical = 16.dp))
                Text("App version")
                Text("1.0", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Text("Support", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 28.dp))
        Text("Questions about an order or collection? Our team is here to help.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(
            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(supportUrl))) },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        ) {
            Icon(Icons.Default.Language, null)
            Text("Customer Support", Modifier.padding(start = 10.dp))
        }
        Text("Legal", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 28.dp))
        Text("Privacy and customer information are available on our company website.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
