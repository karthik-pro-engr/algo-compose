package com.karthik.pro.engr.algocompose.presentation.ui.util

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Modifier.imePaddingScrollModifier(scrollState: ScrollState): Modifier {
    return this
        .verticalScroll(scrollState)
        .imePadding()

}

@Composable
fun Modifier.bringIntoViewOnFocusModifier(
    delayMills: Long = 120L,
    bringIntoViewRequester: BringIntoViewRequester
): Modifier {

    val scope = rememberCoroutineScope()

    return this
        .bringIntoViewRequester(bringIntoViewRequester = bringIntoViewRequester)
        .onFocusChanged {
            if (it.isFocused) {
                scope.launch {
                    delay(delayMills)
                    bringIntoViewRequester.bringIntoView()
                }
            }
        }


}