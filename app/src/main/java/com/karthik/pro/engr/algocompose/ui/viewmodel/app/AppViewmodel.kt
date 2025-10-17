package com.karthik.pro.engr.algocompose.ui.viewmodel.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.appdistribution.FirebaseAppDistribution
import com.karthik.pro.engr.algocompose.R
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class AppViewmodel : ViewModel() {
    private var _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState

    private var _uiEffects = MutableSharedFlow<UiEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val uiEffects = _uiEffects

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

            AppEvent.SendFeedback -> {
                sendFeedBack()
            }
        }
    }

    fun setSubmitting(submittingMessage: String) {
        _appUiState.update {
            it.copy(feedBackState = submittingMessage)
        }

    }

    private fun sendFeedBack() {
        viewModelScope.launch {
            setSubmitting("Opening Feedback")
            try {
                FirebaseAppDistribution.getInstance().startFeedback(R.string.feedback_prompt)
                _uiEffects.emit(UiEffect.ShowToast("Opened feedback UI"))
            }catch (t: Throwable) {
                t.message
                _uiEffects.emit(UiEffect.OpenEmailIntent)
            }finally {
                setSubmitting("Hello testers — tap FAB to send feedback")
            }
        }
    }

    private fun getPackageNameSafe(): String = try {
        "app"
    } catch (t: Throwable) {
        "app"
    }

}
