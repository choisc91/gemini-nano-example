package kr.co.characin.gemini_nano_example

data class Message(
    val type: Int,
    val text: String,
) {
    companion object {
        const val TYPE_PROMPT = 0
        const val TYPE_RESPONSE = 1

        fun prompt(prompt: String) = Message(
            type = TYPE_PROMPT,
            text = prompt,
        )

        fun response(response: String) = Message(
            type = TYPE_RESPONSE,
            text = response,
        )
    }
}