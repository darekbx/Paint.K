package commands

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class Command(
    val label: String = "",
    val icon: ImageVector = Icons.Default.Tornado,
    val isDivider: Boolean = false
) {

    OPEN("Open file", Icons.Filled.FileOpen),
    SAVE("Save", Icons.Filled.Save),
    DIVIDER_1(isDivider = true),
    ZOOM_OUT("Zoom minus", Icons.Default.ZoomOut),
    ZOOM_IN("Zoom plus", Icons.Default.ZoomIn),
    DIVIDER_2(isDivider = true),
    RESIZE("Resize", Icons.Default.Transform),
    CROP("Crop", Icons.Default.Crop),
    DIVIDER_3(isDivider = true),
    INFO("Informations", Icons.Filled.Info),
}