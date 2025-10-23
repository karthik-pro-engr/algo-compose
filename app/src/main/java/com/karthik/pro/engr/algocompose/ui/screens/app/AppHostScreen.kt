package com.karthik.pro.engr.algocompose.ui.screens.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.ui.screens.energy.BalancedEnergyScreen
import com.karthik.pro.engr.algocompose.ui.screens.vsw.VariableSlidingWindowScreen
import com.karthik.pro.engr.algocompose.ui.screens.warehouse.BoxNestingScreen
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppEvent
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppViewmodel
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.ScreenId
import com.karthik.pro.engr.algocompose.ui.viewmodel.vsw.VarSlidingWindowViewModel
import com.karthik.pro.engr.algocompose.ui.viewmodel.warehouse.BoxNestingViewModel

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

            ScreenId.BUDGET_STAY -> {
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
                val varSlidingWindowViewModel: VarSlidingWindowViewModel = viewModel()
                VariableSlidingWindowScreen(
                    modifier = modifier,
                    vm = varSlidingWindowViewModel,
                    lblRangeOrMaxCapacity = R.string.label_budget,
                    phRangeOrMaxCapacity = R.string.placeholder_budget,
                    btnRangeOrMaxCapacity = R.string.button_add_budget,
                    lblInputForArr = R.string.label_stay_input,
                    phInputForArr = R.string.placeholder_stay_input,
                    btnInputForArr = R.string.button_add_hotel_cost,
                    txtInputArrInfo = R.string.text_no_hotel_cost_added,
                    btnComputeResult = R.string.button_find_longest_nights,
                    strPlural = R.plurals.nights,
                    txtMaxCapacity = R.string.text_added_budget,
                    txtResult = R.string.text_stay_result,
                    onBack = { vm.onEvent(AppEvent.OnBack) },
                )
            }

            ScreenId.VIDEO_PLAY_REQUESTS -> {
                /* Internet Data Flow
                  * -------------------
                  * A server is handling incoming video play requests per second (some light, some heavy).
                  * Find the maximum number of consecutive seconds where the total requests stay within the allowed server capacity.
                  *
                  *
                  * */
                val varSlidingWindowViewModel: VarSlidingWindowViewModel = viewModel()
                VariableSlidingWindowScreen(
                    modifier = modifier,
                    vm = varSlidingWindowViewModel,
                    lblRangeOrMaxCapacity = R.string.label_add_request_capacity,
                    phRangeOrMaxCapacity = R.string.placeholder_request_capacity,
                    btnRangeOrMaxCapacity = R.string.button_add_requests_capacity,
                    lblInputForArr = R.string.label_data_request,
                    phInputForArr = R.string.placeholder_input_request,
                    btnInputForArr = R.string.button_add_requests,
                    txtInputArrInfo = R.string.text_no_request_added,
                    btnComputeResult = R.string.button_find_longest_seconds,
                    strPlural = R.plurals.seconds,
                    txtMaxCapacity = R.string.text_added_max_requests,
                    txtResult = R.string.text_request_result,
                    onBack = { vm.onEvent(AppEvent.OnBack) }
                )
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
                    boxNestingViewModel = viewModel<BoxNestingViewModel>(),
                    onBack = { vm.onEvent(AppEvent.OnBack) })
            }


            else -> vm.onEvent(AppEvent.OnBack)


        }
    }

}