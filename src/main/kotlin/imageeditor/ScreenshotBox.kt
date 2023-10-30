package imageeditor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.use
import org.jetbrains.skia.EncodedImageFormat

@Composable
fun rememberScreenshotState() = remember {
    ScreenshotState()
}

class ScreenshotState internal constructor() {

    internal var callback: (() -> ByteArray?)? = null

    fun capture(): ByteArray? {
        return callback?.invoke()
    }
}

@Composable
fun ScreenshotBox(
    screenshotState: ScreenshotState,
    outSize: IntSize,
    format: EncodedImageFormat,
    content: @Composable () -> Unit,
) {

    fun takeScreenshot(
        outSize: IntSize,
        format: EncodedImageFormat,
        content: @Composable () -> Unit
    ): ByteArray? {
        ImageComposeScene(
            width = outSize.width,
            height = outSize.height,
            density = Density(1f),
            content = content
        ).use {
            val image = it.render()
            val imageData = image.encodeToData(format)
            return@takeScreenshot imageData?.bytes
        }
    }

    DisposableEffect(Unit) {
        screenshotState.callback = { takeScreenshot(outSize, format, content) }
        onDispose { screenshotState.callback = null }
    }

    content()
}