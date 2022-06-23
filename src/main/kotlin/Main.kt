import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.Input
import utils.Loader
import utils.SelectableText
import utils.TextResources
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView

@Composable
@Preview
fun App() {
    var filePath by remember { mutableStateOf("") }
    var affiliation by remember { mutableStateOf("") }
    var loaderVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    MaterialTheme {
        Column(Modifier.fillMaxSize()) {
            TopAppBar(title = { Text(text = "Извлечение и разбор аффилиации") })

            Input(
                text = filePath,
                onButtonClicked = {
                    onSelectFolderClicked {
                        affiliation = ""
                        filePath = it
                    }
                },
                onTextChanged = {
                    filePath = it
                }
            )

            Row {
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        coroutineScope.launch {
                            loaderVisible = true
                            delay(7_000)
                            affiliation = TextResources.jatsForExtract
                            loaderVisible = false
                        }
                    }
                ) {
                    Text("Извлечь")
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            loaderVisible = true
                            delay(7_000)
                            affiliation = TextResources.jatsForReplnish
                            loaderVisible = false
                        }
                    }
                ) {
                    Text("Уточнить")
                }
            }

            SelectableText(affiliation)
        }

        Loader(loaderVisible)
    }
}

private fun onSelectFolderClicked(onResult: (path: String) -> Unit) {
    val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory).apply {
        dialogTitle = "Выбрать PDF документ"
        fileSelectionMode = JFileChooser.FILES_ONLY
        fileFilter = FileNameExtensionFilter(null, "pdf")
    }

    val returnValue = fileChooser.showOpenDialog(null)

    if (returnValue == JFileChooser.APPROVE_OPTION) {
        if (fileChooser.selectedFile.isFile) {
            onResult(fileChooser.selectedFile.toString())
        }
    }
}

fun main() = application {
    Window(
        title = "Извлечение и разбор аффилиации",
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
