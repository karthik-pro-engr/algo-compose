package com.karthik.pro.engr.algocompose.ui.screens.warehouse

/***
 * In a town, each house either produces electricity (producer house) or consumes electricity (consumer house).
 * You want to find the longest continuous stretch of houses where the total electricity balances out (no surplus, no deficit).
 */
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.R
import com.karthik.pro.engr.algocompose.ui.components.molecules.StatusText
import com.karthik.pro.engr.algocompose.ui.viewmodel.warehouse.BoxNestingEvent
import com.karthik.pro.engr.algocompose.ui.viewmodel.warehouse.BoxNestingUiState
import com.karthik.pro.engr.algocompose.ui.viewmodel.warehouse.BoxNestingViewModel
import com.karthik.pro.engr.devtools.AllVariantsPreview

@Composable
fun BoxNestingScreen(
    modifier: Modifier = Modifier,
    boxNestingViewModel: BoxNestingViewModel,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(true) }
    val boxNestingUiState by boxNestingViewModel.boxNestingUiState.collectAsState()
    val boxSizesList = boxNestingUiState.boxSizesList

    val scrollState = rememberSaveable (saver = ScrollState.Saver){ ScrollState(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(R.string.box_nesting_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.box_nesting_problem_statement),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            InputTextField(
                Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                input,
                onValueChange = { value -> if (value.all { it.isDigit() }) input = value },
                boxNestingUiState
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    boxNestingViewModel.onEvent(BoxNestingEvent.AddBoxSize(input))
                    input = ""
                },
                enabled = enableAddButton
            ) {
                Text(stringResource(R.string.button_add_delivery_box_size))
            }
        }

        StatusText(
            errorMessage = boxNestingUiState.errorMessage,
            inputMessage = when {
                boxSizesList.isNotEmpty() -> {
                    "[ ${boxSizesList.joinToString(", ") { "$it cc" }} ]"
                }

                else -> stringResource(
                    R.string.text_no_delivery_boxes_added
                )
            }
        )

        if (boxSizesList.size > 1) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                enableAddButton = false
                boxNestingViewModel.onEvent(BoxNestingEvent.ComputeBoxNesting)
            }, enabled = enableAddButton) {
                Text(text = stringResource(R.string.button_find_auto_nest_boxes))
            }
            boxNestingUiState.boxNestingOrder?.let {

                val maxOfDigits =
                    boxSizesList.maxOfOrNull { boxSize -> boxSize.toString().length } ?: 0
                val avgCharsPerLine = 24
                val capacityEstimate = (boxSizesList.size * avgCharsPerLine).coerceAtLeast(64)

                val buildString = buildString(capacityEstimate) {
                    boxSizesList.forEachIndexed { idx, size ->
                        if (idx > 0) append("\n")
                        append("B").append(idx).append(" — ")

                        append(size.toString().padStart(maxOfDigits, ' '))

                        append(" cc -> Next: ")

                        val nextIdx = it.boxNestingOrderList[idx]

                        if (nextIdx == -1) {
                            append("None")
                        } else {
                            val nextBoxSize = boxSizesList.getOrNull(nextIdx) ?: 0
                            append("B").append(nextIdx)
                                .append(" (").append(nextBoxSize).append(" cc) ")
                        }

                    }
                }
                Text(
                    buildString
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            enableAddButton = true
            boxNestingViewModel.onEvent(BoxNestingEvent.Reset)
        }) {
            Text(stringResource(R.string.button_reset))
        }
    }
}

private fun Modifier.debugOverlay(color: Color, tag: String, show: Boolean): Modifier {
    return if (!show) this else {
        this
            .drawBehind {
                // draw translucent rectangle filling the composable bounds
                drawRect(color = color)
                // optional cross hair to see origin
                drawCircle(color = Color.White, radius = 2f, center = Offset(2f, 2f))
            }
            .onGloballyPositioned { coordinates ->
                val size = coordinates.size
                // Log size & position; check Logcat under "LayoutDebug"
                Log.d(
                    "LayoutDebug",
                    "$tag bounds: ${coordinates.boundsInWindow()} size=${size.width}x${size.height}"
                )
            }
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    boxNestingUiState: BoxNestingUiState
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.label_box_size_input)) },
        placeholder = { Text(stringResource(R.string.placeholder_box_size_volume)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = boxNestingUiState.errorMessage.isNotEmpty(),
        modifier = modifier
    )
}

@AllVariantsPreview
@Composable
private fun BalancedEnergyScreenPreview() {
    BoxNestingScreen(boxNestingViewModel = viewModel<BoxNestingViewModel>(), onBack = {})
}