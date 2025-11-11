package com.karthik.pro.engr.algocompose.twopointers.vsw.presentation.ui.components.molecules

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.algocompose.presentation.ui.util.bringIntoViewOnFocusModifier

@Composable
fun InputWithButtonRes(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes labelRes: Int,
    @StringRes placeholderRes: Int,
    @StringRes buttonRes: Int,
    enabled: Boolean = true,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    keyboardActions: KeyboardActions,
    keyboardOptions: () -> KeyboardOptions = { KeyboardOptions.Default }
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()

    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(stringResource(labelRes)) },
            placeholder = { Text(stringResource(placeholderRes)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = keyboardActions,
            isError = isError,
            modifier = Modifier
                .weight(1f)
                .bringIntoViewOnFocusModifier(bringIntoViewRequester = bringIntoViewRequester)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onButtonClick,
            enabled = enabled
        ) {
            Text(stringResource(buttonRes))
        }
    }
}