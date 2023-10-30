import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import ui.MainLayout
import ui.PaintDotKTheme
import java.awt.Toolkit

/**
 * TODO:
 * - save image
 * - resize image
 * - crop image
 * - select part
 * - draw
 *   - color picker
 *   - line thickness
 *   - line
 *   - rectangle
 *   - oval
 */

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Paint.K",
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = getPrefferedWindowSize()
        ),
        //icon = TODO
        //onKeyEvent = TODO
    ) {
        MaterialTheme(colors = PaintDotKTheme.colors.material) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    MainLayout()
                }
            }
        }
    }
}

private fun getPrefferedWindowSize(): DpSize {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    return DpSize(screenSize.width.dp, screenSize.height.dp)
}
