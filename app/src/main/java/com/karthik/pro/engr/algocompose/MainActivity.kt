package com.karthik.pro.engr.algocompose


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karthik.pro.engr.algocompose.ui.screens.app.AppHostScreen
import com.karthik.pro.engr.algocompose.ui.theme.AlgoComposeTheme
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppEvent
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppUiState
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.AppViewmodel
import com.karthik.pro.engr.algocompose.ui.viewmodel.app.UiEffect
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlgoComposeTheme {
                SetContent()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetContent(modifier: Modifier = Modifier) {
    val vm: AppViewmodel = viewModel()
    val uiState by vm.appUiState.collectAsState()
    val activity = LocalActivity.current
    val current = LocalContext.current

    LaunchedEffect(Unit) {
        vm.uiEffects.collectLatest { effect ->
            when (effect) {
                is UiEffect.OpenEmailIntent -> {
                    launchFeedbackEmail(current, "karthik.pro.engr@gmail.com")
                }

                is UiEffect.ShowToast -> Toast.makeText(activity, effect.message, Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    if (BuildConfig.ENABLE_APP_DISTRIBUTION) {
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
                if (BuildConfig.ENABLE_APP_DISTRIBUTION) {
                    Text(
                        uiState.feedBackState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .navigationBarsPadding(),
                        textAlign = TextAlign.Center
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { vm.onEvent(AppEvent.SendFeedback) }) {
                    Icon(Icons.Filled.Feedback, contentDescription = "Send Feedback")
                }
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

fun launchFeedbackEmail(context: Context, to: String = "karthik.pro.engr@gmail.com") {
    val pkg = context.packageName
    val pm = context.packageManager

    // app version info (safe access)
    val (versionName, versionCode) = try {
        val pi = pm.getPackageInfo(pkg, 0)
        val vName = pi.versionName ?: "unknown"
        val vCode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            pi.longVersionCode.toString()
        } else {
            @Suppress("DEPRECATION")
            pi.versionCode.toString()
        }
        vName to vCode
    } catch (t: Throwable) {
        "unknown" to "unknown"
    }

    // device & system info
    val androidVersion = "${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})"
    val deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}".trim()
    val locale = Locale.getDefault().toLanguageTag()
    val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

    val subject = "Beta feedback — $pkg (v:$versionName)"
    val body = """
        Please describe the issue and steps to reproduce.

        App package: $pkg
        App version: $versionName (code: $versionCode)
        Android version: $androidVersion
        Device model: $deviceModel
        Locale: $locale
        Timestamp: $timestamp

        Steps to reproduce:

        Expected:

        Actual:
    """.trimIndent()

    // Create email intent (ACTION_SENDTO + mailto: ensures email apps only)
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri() // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    // Safely start chooser
    val chooser = Intent.createChooser(emailIntent, "Send feedback using")
    if (emailIntent.resolveActivity(pm) != null) {
        // If you call this from an Activity, this will open chooser directly.
        // If using application context (rare), add FLAG_ACTIVITY_NEW_TASK.
        context.startActivity(chooser)
    } else {
        Toast.makeText(context, "No email app installed to send feedback", Toast.LENGTH_SHORT)
            .show()
    }
}

private fun getTitleForScreenId(selectedId: Int, uiState: AppUiState): String {
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