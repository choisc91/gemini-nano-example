package kr.co.characin.gemini_nano_example

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kr.co.characin.gemini_nano_example.ui.theme.GemininanoexampleTheme

class MainActivity : ComponentActivity() {

    private val _viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GemininanoexampleTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val messages = _viewModel.messageFlow
                    val downloaded by _viewModel.downloadedFlow.collectAsState()

                    if (downloaded)
                        PromptPage(
                            paddingValues = innerPadding,
                            messages = messages,
                            onSend = { _viewModel.sendPrompt(it) },
                        )
                    else
                        ModelDownloadPage(
                            paddingValues = innerPadding,
                            onDownload = { _viewModel.ensureModelDownloaded(context = applicationContext) },
                        )
                }

                // DownloadStauts에 따른 UI 관리 Compose.
                val downalodStatus by _viewModel.downloadStatusFlow.collectAsState()
                BuildDownloadStatus(
                    context = applicationContext,
                    downloadStatus = downalodStatus,
                )

                //
                val inProgress by _viewModel.inProgressFlow.collectAsState()
                if (inProgress)
                    BuildProgressDialog()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModel.onDispose()
    }
}

@Composable
fun BuildDownloadStatus(
    context: Context,
    downloadStatus: DownloadStatus,
) {
    when (downloadStatus) {
        DownloadStatus.Success -> Toast.makeText(context, "LLM has been activated and is ready to use", Toast.LENGTH_SHORT).show()
        DownloadStatus.Wait -> Unit
        is DownloadStatus.Fail -> Toast.makeText(context, downloadStatus.message, Toast.LENGTH_SHORT).show()
        is DownloadStatus.InProgress -> BuildDownloadProgressDialog(progressStatus = downloadStatus)
    }
}

@Composable
fun ModelDownloadPage(
    paddingValues: PaddingValues,
    onDownload: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp),
    ) {
        Text(text = "Caution: The current sample project will not work if it is not for the Pixel9 series")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size = 8.dp),
            onClick = onDownload,
            content = { Text("DOWNLOAD LLM") }
        )
    }
}

@Composable
fun PromptPage(
    paddingValues: PaddingValues,
    messages: List<Message>,
    onSend: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp),
    ) {
        MainUi.BuildMessagesContainer(
            modifier = Modifier.weight(1f),
            messages = messages,
        )

        var prompt by remember { mutableStateOf(value = "") }
        MainUi.BuildInputPromptField(
            prompt = prompt,
            onPromptChange = { prompt = it },
        )

        Spacer(modifier = Modifier.width(6.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size = 8.dp),
            onClick = {
                onSend(prompt)
                prompt = ""
            },
            content = { Text("SEND") }
        )
    }
}

@Composable
fun BuildDownloadProgressDialog(
    progressStatus: DownloadStatus.InProgress,
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Text(text = progressStatus.message)

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { progressStatus.progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(color = 0xffB93920),
                    trackColor = Color(color = 0xffC8C8C8),
                )
            }
        }
    }
}

@Composable
fun BuildProgressDialog() {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = 32.dp),
                color = Color(color = 0xffB93920),
                trackColor = Color(color = 0xffECECEC),
            )
        }
    }
}