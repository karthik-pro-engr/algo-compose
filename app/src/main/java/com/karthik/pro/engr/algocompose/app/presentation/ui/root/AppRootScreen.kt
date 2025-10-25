package com.karthik.pro.engr.algocompose.app.presentation.ui.root

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

/**
 * Top-level root wrapper used by screens.
 *
 * - Provides optional "tap anywhere to dismiss keyboard" behavior
 * - Supplies a small helper lambda to child content so screens can programmatically hide keyboard
 *
 * @param useTapToClear whether tapping empty space hides keyboard/focus (default true).
 * @param contentPadding outer padding (default 16.dp).
 * @param clickableIndication when false we disable ripple (default null-style behavior).
 * @param content slot that receives a `hideKeyboardAndClearFocus` lambda.
 */

@Composable
fun AppRootScreen(
    modifier: Modifier = Modifier,
    useTapToClear: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    clickableIndicationEnabled: Boolean = false,
    content: @Composable BoxScope.(hideKeyboardAndClearFocus: () -> Unit) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val interactionSource = remember { MutableInteractionSource() }

    val hideKeyboardAndClearFocus = remember(focusManager, keyboardController) {
        {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
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
        content(hideKeyboardAndClearFocus)
    }

}