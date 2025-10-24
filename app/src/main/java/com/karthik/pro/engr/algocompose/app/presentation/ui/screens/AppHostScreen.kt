package com.karthik.pro.engr.algocompose.app.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.app.presentation.ui.components.atoms.AppButton
import com.karthik.pro.engr.algocompose.twopointers.prefixsum.presentation.ui.screens.BalancedEnergyScreen
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.screens.VariableSlidingWindowScreenWrapper
import com.karthik.pro.engr.algocompose.stack.nge.presentation.ui.screens.BoxNestingScreen
import com.karthik.pro.engr.algocompose.app.presentation.model.AppEvent
import com.karthik.pro.engr.algocompose.app.presentation.viewmodel.AppViewmodel
import com.karthik.pro.engr.algocompose.app.presentation.model.ScreenId
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.viewmodel.VarSlidingWindowViewModel
import com.karthik.pro.engr.algocompose.stack.nge.presentation.viewmodel.BoxNestingViewModel
import com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.model.VswStrings

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
                val vswStrings = VswStrings(
                    titleRes = R.string.vsw_budget_title,
                    bodyRes = R.string.vsw_budget_body,
                    capacityLabelRes = R.string.vsw_budget_capacity_label,
                    capacityPlaceholderRes = R.string.vsw_budget_capacity_placeholder,
                    capacityButtonRes = R.string.vsw_budget_button_add,
                    itemLabelRes = R.string.vsw_budget_label_input,
                    itemPlaceholderRes = R.string.vsw_budget_placeholder_input,
                    itemButtonRes = R.string.vsw_budget_button_add_hotel_cost,
                    noItemsInfoRes = R.string.vsw_budget_no_items_info,
                    computeButtonRes = R.string.vsw_budget_button_compute,
                    unitPluralRes = R.plurals.vsw_budget_nights,
                    capacityAddedTextRes = R.string.vsw_budget_capacity_added,
                    resultTextRes = R.string.vsw_budget_result,
                )
                VariableSlidingWindowScreenWrapper(
                    modifier = modifier,
                    vm = varSlidingWindowViewModel,
                    vswStrings = vswStrings,
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

                val vswStrings = VswStrings(
                    titleRes = R.string.vsw_video_title,
                    bodyRes = R.string.vsw_video_body,
                    capacityLabelRes = R.string.vsw_video_capacity_label_add,
                    capacityPlaceholderRes = R.string.vsw_video_capacity_placeholder,
                    capacityButtonRes = R.string.vsw_video_capacity_button_add,
                    itemLabelRes = R.string.vsw_video_label_input,
                    itemPlaceholderRes = R.string.vsw_video_placeholder_input,
                    itemButtonRes = R.string.vsw_video_button_add_input,
                    noItemsInfoRes = R.string.vsw_video_no_input_infos,
                    computeButtonRes = R.string.vsw_video_button_compute,
                    unitPluralRes = R.plurals.vsw_video_seconds,
                    capacityAddedTextRes = R.string.vsw_video_capacity_added,
                    resultTextRes = R.string.vsw_video_result,
                )

                VariableSlidingWindowScreenWrapper(
                    modifier = modifier,
                    vm = varSlidingWindowViewModel,
                    vswStrings = vswStrings,
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