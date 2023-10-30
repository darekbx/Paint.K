package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import commands.Command
import di.MiniDi
import imageeditor.ImageEditor
import imageeditor.ImageEditorView
import ui.dialogs.ResizeDialog

@Composable
fun MainLayout(
    imageEditor: ImageEditor = MiniDi.imageEditor
) {
    var showResizeDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        CommandsRow(modifier = Modifier.fillMaxWidth()) { command ->
            when(command) {
                Command.RESIZE -> showResizeDialog = true
                else -> {}
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.fillMaxSize().weight(1F)) {
            ToolsStrip(modifier = Modifier.fillMaxHeight())
            ImageEditorView(modifier = Modifier.fillMaxSize().weight(1F), imageEditor)
        }
        Divider(modifier = Modifier.fillMaxWidth())
        StatusBar(modifier = Modifier.fillMaxWidth(), imageEditor)
    }


    if (showResizeDialog) {
        // TODO add lambda to handle close (showResizeDialog), pass imageEditor
        ResizeDialog()
    }
}

@Composable
private fun CommandsRow(
    modifier: Modifier = Modifier,
    imageEditor: ImageEditor = MiniDi.imageEditor,
    onDialogAction: (Command) -> Unit
) {
    var showFilePicker by remember { mutableStateOf(false) }
    var showDirectoryPicker by remember { mutableStateOf(false) }

    CommandBar(modifier = modifier) { command ->
        when (command) {
            Command.OPEN -> showFilePicker = true
            Command.SAVE -> showDirectoryPicker = true
            Command.RESIZE -> onDialogAction(command)
            Command.CROP -> TODO()
            Command.INFO -> TODO()
            Command.ZOOM_OUT -> imageEditor.zoomOut()
            Command.ZOOM_IN -> imageEditor.zoomIn()
            else -> { }
        }
    }

    FilePicker(showFilePicker, fileExtensions = ImageEditor.SUPPORTED_EXTENSIONS) {
        showFilePicker = false
        imageEditor.loadFile(it)
    }

    DirectoryPicker(showDirectoryPicker) {
        showDirectoryPicker = false
        imageEditor.saveFile(it, "paint_dot_k.jpg")
    }
}