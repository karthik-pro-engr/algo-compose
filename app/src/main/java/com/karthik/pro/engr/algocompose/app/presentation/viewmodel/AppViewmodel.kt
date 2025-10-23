package com.karthik.pro.engr.algocompose.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.karthik.pro.engr.algocompose.app.presentation.model.AppEvent
import com.karthik.pro.engr.algocompose.app.presentation.model.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AppViewmodel : ViewModel() {
    private var _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState


    fun onEvent(event: AppEvent) {

        when (event) {
            is AppEvent.SelectedScreen ->
                _appUiState.update {
                    it.copy(selectedScreenId = event.id)
                }

            AppEvent.OnBack -> {
                _appUiState.update {
                    it.copy(selectedScreenId = null)
                }
            }


        }
    }


}