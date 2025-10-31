package com.karthik.pro.engr.algocompose.app.presentation.ui.root

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.algocompose.presentation.ui.util.imePaddingScrollModifier

/**
 * Top-level root wrapper used by screens.
 *
 * - Provides optional "tap anywhere to dismiss keyboard" behavior
 * - Supplies a small helper lambda to child content so screens can programmatically hide keyboard
 *
 * @param useTapToClear whether tapping empty space hides keyboard/focus (default true).
 * @param contentPadding outer padding (default 16.dp).
 * @param clickableIndicationEnabled when false we disable ripple (default null-style behavior).
 * @param content slot that receives a `hideKeyboardAndClearFocus` lambda.
 */

@Composable
fun AppRootScreen(
    modifier: Modifier = Modifier,
    useTapToClear: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    clickableIndicationEnabled: Boolean = false,
    contentScrollable: Boolean = true,
    content: @Composable ColumnScope.(hideKeyboardAndClearFocus: () -> Unit) -> Unit
) {

    val scrollState = rememberScrollState()


    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val interactionSource = remember { MutableInteractionSource() }

    val hideKeyboardAndClearFocus = remember(focusManager, keyboardController) {
        {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }
    val rootScroll = if (contentScrollable) rememberScrollState() else null
    Column(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (useTapToClear) {
                    Modifier.clickable(
                        indication = if (clickableIndicationEnabled) LocalIndication.current else null,
                        interactionSource = interactionSource
                    ) {
                        hideKeyboardAndClearFocus()
                    }
                } else Modifier
            )
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .then(if (rootScroll == null) Modifier else Modifier.verticalScroll(scrollState))
                .imePaddingScrollModifier(scrollState)
        ) {
            content(hideKeyboardAndClearFocus)
        }

    }
}