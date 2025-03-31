package kr.co.characin.gemini_nano_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

class MainUi {
    companion object {
        @Composable
        fun BuildMessagesContainer(
            modifier: Modifier,
            messages: List<Message>,
        ) {
            val listState = rememberLazyListState()
            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty())
                    listState.animateScrollToItem(messages.lastIndex)
            }

            LazyColumn(
                modifier = modifier,
                state = listState
            ) {
                items(count = messages.size) { index ->
                    BuildMessageItem(message = messages[index])
                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        @Composable
        fun BuildInputPromptField(
            prompt: String,
            onPromptChange: (String) -> Unit,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = prompt,
                shape = RoundedCornerShape(4.dp),
                onValueChange = onPromptChange,
                trailingIcon = {
                    IconButton(
                        onClick = { onPromptChange("") },
                        content = {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                contentDescription = "clear",
                                imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                            )
                        },
                    )
                }
            )
        }

        @Composable
        fun BuildMessageItem(message: Message) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (message.type == Message.TYPE_PROMPT) Arrangement.End else Arrangement.Start,
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = if (message.type == Message.TYPE_PROMPT)
                                Color.Blue
                            else
                                Color.Yellow,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(8.dp),
                    horizontalAlignment = if (message.type == Message.TYPE_PROMPT) Alignment.End else Alignment.Start,
                ) {
                    Text(
                        message.text,
                        color = if (message.type == Message.TYPE_PROMPT)
                            Color.White
                        else
                            Color.Black,
                    )
                }
            }
        }
    }
}