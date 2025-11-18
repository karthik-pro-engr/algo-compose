package com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.app.presentation.ui.root.AppRootScreen
import com.karthik.pro.engr.algocompose.domain.stack.MonotonicStackProcessor
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.model.MonotonicScreenConfig
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.ui.MonotonicScreenWrapper
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.viewmodel.MonotonicViewModel
import com.karthik.pro.engr.algocompose.stack.monotonic.presentation.viewmodel.MonotonicViewModelFactory
import com.karthik.pro.engr.algocompose.util.bigDecimalValidator
import com.karthik.pro.engr.algocompose.util.numberKeyboardOption
import java.math.BigDecimal

@Composable
fun StockPriceWatcherScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {

    val monotonicViewModelFactory = MonotonicViewModelFactory(
        MonotonicStackProcessor::computePreviousGreaterElement,
        parser = { s -> s.toBigDecimalOrNull() })
    val monotonicViewModel: MonotonicViewModel<BigDecimal> =
        viewModel(key = "Stock Price Watcher", factory = monotonicViewModelFactory)

    val monotonicScreenConfig = MonotonicScreenConfig<BigDecimal>(
        titleRes = R.string.monotonic_stock_title,
        bodyRes = R.string.monotonic_stock_body,
        inputLabelRes = R.string.monotonic_stock_label_input,
        inputPlaceholderRes = R.string.monotonic_stock_placeholder_input,
        inputButtonRes = R.string.monotonic_stock_button_add_input,
        noItemInfosRes = R.string.monotonic_stock_no_items_info,
        computeButtonRes = R.string.monotonic_stock_button_compute,
        inputValidator = bigDecimalValidator,
        keyboardOptionsProvider = numberKeyboardOption,
        formatResultLine = { resultFormat ->
            with(resultFormat) {
                val day = actualIndex + 1
                val price = actualValue
                val spanText = if (computedIndex == -1) "None" else {
                    val span = computedIndex - actualIndex - 1
                    when {
                        span == 1 -> "1 day"
                        else -> "$span days"
                    }
                }
                "Day $day (price=$price) -> $spanText"
            }
        }
    )
    AppRootScreen(modifier = modifier, contentScrollable = false) { hideKeyboard ->
        MonotonicScreenWrapper(
            monotonicViewModel = monotonicViewModel,
            monotonicScreenConfig = monotonicScreenConfig,
            hideKeyboard = hideKeyboard,
            onBack = onBack
        )
    }
}