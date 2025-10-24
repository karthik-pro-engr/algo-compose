package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.karthik.pro.engr.algocompose.domain.vsw.ConsecutiveSubArrayAndSize

@Composable
fun ComputeAndResultSection(
    modifier: Modifier = Modifier,
    @StringRes buttonRes: Int,
    @PluralsRes unitPluralRes: Int,
    @StringRes resultRes: Int,
    enabled: Boolean = true,
    result: ConsecutiveSubArrayAndSize?,
    onButtonClicked: () -> Unit,
) {
    Button(
        onClick = onButtonClicked,
        enabled = enabled
    ) {
        Text(text = stringResource(buttonRes))
    }
    result?.let {
        val longestStretch =
            pluralStringResource(unitPluralRes, it.maxStretch, it.maxStretch)
        Text(
            stringResource(
                resultRes,
                it.startIndex + 1,
                it.endIndex + 1,
                longestStretch
            )
        )
    }
}