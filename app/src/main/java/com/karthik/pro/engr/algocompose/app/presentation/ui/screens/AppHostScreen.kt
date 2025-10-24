package com.karthik.pro.engr.algocompose.app.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.app.presentation.model.AppEvent
import com.karthik.pro.engr.algocompose.app.presentation.model.ScreenId
import com.karthik.pro.engr.algocompose.app.presentation.ui.components.atoms.AppButton
import com.karthik.pro.engr.algocompose.app.presentation.viewmodel.AppViewmodel
import com.karthik.pro.engr.algocompose.stack.nge.presentation.ui.screens.BoxNestingScreen
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens.BalancedEnergyScreen
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens.BudgetStayScreen
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens.VideoRequestsScreen

@Composable
fun AppHostScreen(
    modifier: Modifier = Modifier,
    vm: AppViewmodel = viewModel()
) {
    val collectAsState by vm.appUiState.collectAsState()

    if (collectAsState.selectedScreenId == null) {
        Column(modifier = modifier) {
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

            else -> vm.onEvent(AppEvent.OnBack)


        }
    }

}