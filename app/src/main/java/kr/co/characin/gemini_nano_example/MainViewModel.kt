package kr.co.characin.gemini_nano_example

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.edge.aicore.DownloadCallback
import com.google.ai.edge.aicore.DownloadConfig
import com.google.ai.edge.aicore.GenerativeAIException
import com.google.ai.edge.aicore.GenerativeModel
import com.google.ai.edge.aicore.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.String.format
import java.util.Locale

class MainViewModel : ViewModel() {

    companion object {
        private const val MEGABYTE = 1024 * 1024
        private const val MSG_UNKNWON_EXCEPTION = "An unknown error occurred"
    }

    private val _inProgressFlow = MutableStateFlow(value = false)
    val inProgressFlow: StateFlow<Boolean> get() = _inProgressFlow

    // LLM 모델의 다운로드 여부
    private val _downloadedFlow = MutableStateFlow(value = false)
    val downloadedFlow: StateFlow<Boolean> get() = _downloadedFlow

    // 다운로드 상태.
    private val _downloadStatusFlow = MutableStateFlow<DownloadStatus>(value = DownloadStatus.Wait)
    val downloadStatusFlow: StateFlow<DownloadStatus> get() = _downloadStatusFlow

    //
    private val _messagesFlow = mutableStateListOf<Message>()
    val messageFlow: List<Message> get() = _messagesFlow

    // 생성된 Gemini nano on device 모델.
    private lateinit var _generativeModel: GenerativeModel

    // 모델 생성에 필요한 LLM 모델을 다운로드 받고, 사용 가능한 시점에 활성화 합니다.
    fun ensureModelDownloaded(context: Context) {
        var totalBytesToDownload = 0L
        val downloadCallback = object : DownloadCallback {

            override fun onDownloadStarted(bytesToDownload: Long) {
                totalBytesToDownload = bytesToDownload
            }

            override fun onDownloadProgress(totalBytesDownloaded: Long) {
                if (totalBytesToDownload > 0) {
                    val persent = 100.0 * totalBytesDownloaded / totalBytesToDownload
                    val message = format(
                        Locale.ENGLISH,
                        "Downloading :  %d / %d MB (%.2f%%)",
                        totalBytesDownloaded / MEGABYTE,    // 남은 양.
                        totalBytesToDownload / MEGABYTE,    // 받은 양.
                        persent,                            // 진행율.
                    )
                    setDownloadStatus(status = DownloadStatus.InProgress(message, persent.toFloat()))
                }
            }

            override fun onDownloadCompleted() {
                _downloadedFlow.value = true
                setDownloadStatus(status = DownloadStatus.Success)
            }

            override fun onDownloadFailed(failureStatus: String, e: GenerativeAIException) {
                setDownloadStatus(status = DownloadStatus.Fail(message = "$failureStatus, ${e.message}"))
            }
        }

        _generativeModel = GenerativeModel(buildGenerationConfiguration(context), DownloadConfig(downloadCallback))

        viewModelScope.launch {
            try {
                _generativeModel.prepareInferenceEngine()

            } catch (e: GenerativeAIException) {
                setDownloadStatus(status = DownloadStatus.Fail(message = e.message ?: MSG_UNKNWON_EXCEPTION))
            }
        }
    }

    //
    fun sendPrompt(prompt: String) {
        if (prompt.isEmpty())
            return

        try {
            viewModelScope.launch {
                setInProgress(true)
                addPrompt(prompt)

                // 비동기 방식.
                val response = _generativeModel.generateContent(prompt)
                addResponse(response = response.text ?: "error :(")
                setInProgress(false)

                // Stream 방식.
//                _generativeModel
//                    .generateContentStream(prompt)
//                    .onCompletion {  }
//                    .collect { response ->
//                        // TODO, update reponse
//                    }
            }

        } catch (e: Exception) {
            addResponse(response = "error :(")
            setInProgress(false)
        }
    }

    // 생성된 모델을 닫습니다.
    fun onDispose() {
        if (::_generativeModel.isInitialized)
            _generativeModel.close()
    }

    // Generative model의 configuration을 반환합니다.
    private fun buildGenerationConfiguration(context: Context) = generationConfig {
        // 뭔지 몰라서, 기존 샘플 프로젝트에서 설정된 값을 그대로 사용 함.
        this.context = context
        temperature = 0.2f
        topK = 16
        maxOutputTokens = 256
    }

    private fun setInProgress(inProgress: Boolean) {
        _inProgressFlow.value = inProgress
    }

    private fun addPrompt(prompt: String) {
        _messagesFlow.add(element = Message.prompt(prompt))
    }

    private fun addResponse(response: String) {
        _messagesFlow.add(element = Message.response(response))
    }

    private fun setDownloadStatus(status: DownloadStatus) {
        _downloadStatusFlow.value = status
    }
}