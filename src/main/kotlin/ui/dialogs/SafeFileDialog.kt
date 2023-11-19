package ui.dialogs

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import ui.PaintDotKTheme

val EXTENSIONS = listOf(".jpg", ".png")

@Composable
fun SaveFileDialog(
    initialName: String = "image_${System.currentTimeMillis()}",
    onSave: (String, String) -> Unit = { _, _ -> },
    onCancel: () -> Unit = { }
) {
    var showDirectoryPicker by remember { mutableStateOf(false) }
    var directory by remember { mutableStateOf("~/Downloads") }
    var fileName by remember { mutableStateOf(initialName) }
    var extension by remember { mutableStateOf(".jpg") }

    DialogBox(modifier = Modifier.width(700.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            DialogTitle("Save file")

            Row(verticalAlignment = Alignment.CenterVertically) {
                RowLabel("Location: ")
                ClickableText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PaintDotKTheme.colors.material.surface, RoundedCornerShape(4.dp))
                        .padding(16.dp),
                    text = AnnotatedString(directory),
                    style = TextStyle(color = PaintDotKTheme.colors.material.onSurface),
                    onClick = { showDirectoryPicker = true },
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RowLabel("File name: ")
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fileName,
                    singleLine = true,
                    onValueChange = { fileName = it },
                    shape = RoundedCornerShape(4.dp),
                    colors = dialogTextFieldColors()
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RowLabel("Extension: ")
                ExtensionSelect { ext ->
                    extension = ext
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.End) {
                Button(onClick = onCancel) {
                    Text("Cancel", color = PaintDotKTheme.colors.material.onSurface)
                }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { onSave(directory, "$fileName$extension") }) {
                    Text("Save", color = PaintDotKTheme.colors.material.onSurface)
                }
            }
        }
    }

    DirectoryPicker(showDirectoryPicker) { location ->
        showDirectoryPicker = false
        location?.let { directory = it }
    }
}

@Composable
private fun ExtensionSelect(onExtensionChanged: (String) -> Unit = { }) {
    var expanded by remember { mutableStateOf(false) }
    var extensionIndex by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier.fillMaxWidth().clickable { expanded = true },
        contentAlignment = Alignment.CenterEnd
    ) {
        Box {
            Text(
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.CenterEnd)
                    .background(PaintDotKTheme.colors.material.surface, RoundedCornerShape(4.dp))
                    .padding(16.dp),
                text = EXTENSIONS[extensionIndex],
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                EXTENSIONS.forEach { ext ->
                    DropdownMenuItem(onClick = {
                        extensionIndex = EXTENSIONS.indexOf(ext)
                        expanded = false
                        onExtensionChanged(ext)
                    }) {
                        Text(ext)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SaveFileDialogPreview() {
    MaterialTheme(colors = PaintDotKTheme.colors.material) {
        Surface(modifier = Modifier) {
            SaveFileDialog()
        }
    }
}