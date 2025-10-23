package com.karthik.pro.engr.algocompose


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.feedback.AppFeedbackController
import com.karthik.pro.engr.algocompose.feedback.FeedbackBinder.provideAppFeedbackController
import com.karthik.pro.engr.algocompose.app.presentation.ui.screens.AppHostScreen
import com.karthik.pro.engr.algocompose.ui.theme.AlgoComposeTheme
import com.karthik.pro.engr.algocompose.app.presentation.model.AppEvent
import com.karthik.pro.engr.algocompose.app.presentation.model.AppUiState
import com.karthik.pro.engr.algocompose.app.presentation.viewmodel.AppViewmodel
import com.karthik.pro.engr.algocompose.app.presentation.model.ScreenId
import com.karthik.pro.engr.feedback.api.ui.screens.FeedbackFab
import com.karthik.pro.engr.feedback.api.ui.screens.FeedbackStateText
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackEvent
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiEffect

import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private lateinit var feedbackController: AppFeedbackController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedbackController = provideAppFeedbackController(this)
        enableEdgeToEdge()
        setContent {
            AlgoComposeTheme {
                SetContent(appFeedbackController = feedbackController)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetContent(
    modifier: Modifier = Modifier,
    appFeedbackController: AppFeedbackController
) {
    val vm: AppViewmodel = viewModel()
    val uiState by vm.appUiState.collectAsState()
    val current = LocalContext.current
    val feedbackUiState by appFeedbackController.uiState.collectAsState()
    Log.d("MainActivity", "SetContent: $feedbackUiState")
    if (BuildConfig.ENABLE_APP_DISTRIBUTION) {
        LaunchedEffect(Unit) {
            appFeedbackController.uiEffect.collectLatest { effect ->
                when (effect) {
                    FeedbackUiEffect.LaunchEmail -> {
                        appFeedbackController.feedbackActions.launchFeedbackEmail(
                            current,
                            "karthik.pro.engr@gmail.com"
                        )
                    }
                }

            }
        }
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind { drawRect(Color(0x2200FF00)) },
            topBar = {
                uiState.selectedScreenId?.let { selectedId ->
                    TopAppBar(
                        title = { Text(getTitleForScreenId(selectedId, uiState)) },
                        navigationIcon = {
                            IconButton({ vm.onEvent(AppEvent.OnBack) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            },
            bottomBar = {
                FeedbackStateText(modifier, feedbackUiState)
            },
            floatingActionButton = {
                FeedbackFab(
                    modifier,
                    { messageResId ->
                        appFeedbackController.onEvent(
                            FeedbackEvent.SendFeedback(
                                messageResId
                            )
                        )
                    })
            }
        ) { innerPadding ->
            AppHostScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                vm = vm
            )
        }
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind { drawRect(Color(0x2200FF00)) },
            topBar = {
                uiState.selectedScreenId?.let { selectedId ->
                    TopAppBar(
                        title = { Text(getTitleForScreenId(selectedId, uiState)) },
                        navigationIcon = {
                            IconButton({ vm.onEvent(AppEvent.OnBack) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            },
        ) { innerPadding ->
            AppHostScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                vm = vm
            )
        }
    }
}


private fun getTitleForScreenId(selectedId: ScreenId, uiState: AppUiState): String {
    val model = uiState.list.find { it.id == selectedId }
    return model?.buttonName ?: "Details"
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlgoComposeTheme {
        Greeting("Android")
    }
}