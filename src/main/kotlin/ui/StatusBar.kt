package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import imageeditor.ImageEditor

@Composable
fun StatusBar(modifier: Modifier = Modifier, imageEditor: ImageEditor) {
    Row(
        modifier.height(32.dp).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        with (imageEditor.imageSize.value) {
            Text("$width x $height", fontSize = 12.sp)
        }
        Spacer(Modifier.width(16.dp))
        Text("${(imageEditor.zoom.value * 100).toInt()}%", fontSize = 12.sp)
    }
}