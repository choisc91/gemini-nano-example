package kr.co.characin.gemini_nano_example

sealed class DownloadStatus {

    data object Wait : DownloadStatus()

    data object Success : DownloadStatus()

    data class Fail(val message: String) : DownloadStatus()

    data class InProgress(val message: String, val progress: Float) : DownloadStatus()

}