package com.karthik.pro.engr.algocompose

import android.os.Bundle
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.ui.screens.app.AppHostScreen
import com.karthik.pro.engr.algocompose.ui.theme.AlgoComposeTheme
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppEvent
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppUiState
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppViewmodel

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: AppViewmodel = viewModel()
            val uiState by vm.appUiState.collectAsState()
            AlgoComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
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
                    }) { innerPadding ->
                    AppHostScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        vm = vm
                    )
                }
            }
        }
    }

    private fun getTitleForScreenId(selectedId: Int, uiState: AppUiState): String {
        val model = uiState.list.find { it.id == selectedId }
        return model?.buttonName ?: "Details"
    }
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