package com.karthik.pro.engr.algocompose.app.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.app.presentation.model.AppEvent
import com.karthik.pro.engr.algocompose.app.presentation.model.ScreenId
import com.karthik.pro.engr.algocompose.app.presentation.ui.components.atoms.AppButton
import com.karthik.pro.engr.algocompose.app.presentation.viewmodel.AppViewmodel
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.screens.BoxNestingScreen
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.screens.RiverGaugeScreen
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.screens.StockPriceWatcherScreen
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.screens.WindGustsScreen
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens.BalancedEnergyScreen
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens.FuelTankBalancerScreen
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens.SatelliteSignalBalancerScreen
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens.BudgetStayScreen
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens.MusicPlaylistScreen
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens.VideoRequestsScreen

@Composable
fun AppHostScreen(
    modifier: Modifier = Modifier,
    vm: AppViewmodel = viewModel()
) {
    val collectAsState by vm.appUiState.collectAsState()

    if (collectAsState.selectedScreenId == null) {
        Column(
            modifier = modifier.padding(61.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
            ) {
                items(items = vm.appUiState.value.list) {
                    AppButton(
                        buttonName = it.buttonName,
                        onClick = { vm.onEvent(AppEvent.SelectedScreen(it.id)) })
                }
            }
        }
    } else {
        when (collectAsState.selectedScreenId) {
            ScreenId.BALANCED_ENERGY -> BalancedEnergyScreen(
                modifier = modifier,
                onBack = { vm.onEvent(AppEvent.OnBack) })

            /*
            * Budget Stay
            * ______________

            * Family Vacation – Hotel Bookings
            * You’re planning a road trip with your family. Along the highway, there are hotels in sequence, and each hotel has a different nightly rate depending on amenities, demand, and location.
            * You want to book a stretch of consecutive nights (one hotel per night) such that the total stay cost fits within your travel budget, while maximizing the number of nights.
            *
            * ___________________________________
            *
            */

            ScreenId.BUDGET_STAY -> {

                BudgetStayScreen(
                    modifier = modifier,
                    onBack = { vm.onEvent(AppEvent.OnBack) })
            }


            /* Internet Data Flow
                 * -------------------
                 * A server is handling incoming video play requests per second (some light, some heavy).
                 * Find the maximum number of consecutive seconds where the total requests stay within the allowed server capacity.
                 *
                 *
                 * */
            ScreenId.VIDEO_PLAY_REQUESTS -> {

                VideoRequestsScreen(
                    modifier = modifier,
                    onBack = { vm.onEvent(AppEvent.OnBack) })
            }


            /**
             * A regional fulfillment warehouse receives mixed batches of empty shipping cartons on a conveyor belt.
             * To reduce pallet height, protect fragile items, and speed up downstream packing,
             * staff want to automatically identify opportunities to nest smaller cartons into the next larger carton that appears later on the conveyor.
             * This matching is used by the packing robot and operators to decide whether to nest, hold for a different shipment, or mark for repack.
             * */
            ScreenId.BOX_NESTING -> {
                BoxNestingScreen(
                    modifier = modifier,
                    onBack = { vm.onEvent(AppEvent.OnBack) })
            }

            /*
            * Weather Station — Wind Gusts: Minutes Until Next Stronger Gust
            * Your weather station samples wind gust speed in kilometers per hour (km/h) once every minute.
            * For each recorded gust, determine how many minutes you must wait until a later gust is strictly stronger than the current one.
            * If there is no later gust that is strictly stronger, return 0 for that reading.
            * */

            ScreenId.WIND_GUSTS -> {
                WindGustsScreen(modifier = modifier) { vm.onEvent(AppEvent.OnBack) }
            }

            ScreenId.RIVER_GAUGE -> {
                RiverGaugeScreen(modifier = modifier) { vm.onEvent(AppEvent.OnBack) }
            }

            ScreenId.FUEL_TANK_BALANCER -> FuelTankBalancerScreen(
                modifier = modifier,
                onBack = { vm.onEvent(AppEvent.OnBack) })

            ScreenId.MUSIC_PLAYLIST_VARIETY -> MusicPlaylistScreen(
                modifier = modifier,
                onBack = { vm.onEvent(AppEvent.OnBack) })

            /*Stock Price Watcher:
            A trader tracks daily stock prices. For each day, compute the number of consecutive days immediately before that day whose prices were lower than or equal to that day’s price.
             The count excludes the current day. Input: an ordered list of prices; Output: for each day, an integer count. Example: prices = [5, 1, 1, 1, 1, 2] → counts = [0, 0, 1, 2, 3, 4].
            *
            * */
            ScreenId.STOCK_PRICE_WATCHER -> {
                StockPriceWatcherScreen(modifier = modifier) { vm.onEvent(AppEvent.OnBack) }
            }


            ScreenId.SATELLITE_SIGNAL_BALANCE -> SatelliteSignalBalancerScreen(
                modifier = modifier,
                onBack = { vm.onEvent(AppEvent.OnBack) })

            else -> vm.onEvent(AppEvent.OnBack)


        }
    }

}