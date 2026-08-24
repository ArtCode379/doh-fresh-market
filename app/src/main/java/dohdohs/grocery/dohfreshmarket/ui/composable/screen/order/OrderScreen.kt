package dohdohs.grocery.dohfreshmarket.ui.composable.screen.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dohdohs.grocery.dohfreshmarket.R
import dohdohs.grocery.dohfreshmarket.data.entity.OrderEntity
import dohdohs.grocery.dohfreshmarket.ui.composable.shared.HUFFNContentWrapper
import dohdohs.grocery.dohfreshmarket.ui.composable.shared.HUFFNEmptyView
import dohdohs.grocery.dohfreshmarket.ui.state.DataUiState
import dohdohs.grocery.dohfreshmarket.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    OrdersContent(
        ordersState = ordersState,
        modifier = modifier,
    )
}

@Composable
private fun OrdersContent(
    ordersState: DataUiState<List<OrderEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {

        HUFFNContentWrapper(
            dataState = ordersState,

            dataPopulated = {
                val data = (ordersState as DataUiState.Populated).data

            },

            dataEmpty = {
                HUFFNEmptyView(
                    primaryText = stringResource(R.string.huffn_orders_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}