package utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp
import onKeyUp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Input(
    text: String,
    onTextChanged: (String) -> Unit,
    onButtonClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = text,
            modifier = Modifier
                .weight(weight = 1F)
                .onKeyUp(key = Key.Enter, action = onButtonClicked),
            singleLine = true,
            onValueChange = onTextChanged,
            label = { Text(text = "Путь к файлу") }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = onButtonClicked) {
            Text("Выбрать")
        }
    }
}

@Composable
fun SelectableText(text: String) {
    SelectionContainer {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun Loader(isVisible: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(isVisible) {
            CircularProgressIndicator()
        }
    }
}