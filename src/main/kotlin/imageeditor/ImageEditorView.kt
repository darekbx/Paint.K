package imageeditor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.EncodedImageFormat
import ui.pxToDp

@Composable
fun ImageEditorView(
    modifier: Modifier,
    imageEditor: ImageEditor
) {
    val state by imageEditor.uiState.collectAsState(ImageInstanceState.Idle)
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(207, 207, 207, 255))
            .clip(RectangleShape)
            .onSizeChanged { parentSize = it },
        contentAlignment = Alignment.Center
    ) {
        state.let { uiState ->
            when (uiState) {
                ImageInstanceState.Error -> Text("Error")
                ImageInstanceState.Idle -> EditorCanvas(parentSize, imageEditor)
                is ImageInstanceState.Done -> EditorCanvas(parentSize, imageEditor, uiState.image)
                ImageInstanceState.Loading -> CircularProgressIndicator(Modifier.size(60.dp))
            }
        }
    }
}

@Composable
private fun EditorCanvas(
    parentSize: IntSize,
    imageEditor: ImageEditor,
    image: ImageBitmap? = null,
) {

    // Initial size is taken from loaded image or from parent window size,
    // expanded by one pixel to apply scaling
    val scaleOffset = if (image == null) 0.4F else 0.1F
    val initialSize = IntSize(
        image?.width ?: (parentSize.width + 1),
        image?.height ?: (parentSize.height + 1)
    )

    val initialScale: Float = Utils.calculateInitialScale(initialSize, parentSize, scaleOffset)
    val canvasSize = IntSize(
        (initialSize.width * initialScale).toInt(),
        (initialSize.height * initialScale).toInt()
    )

    // Set initial size and zoom
    imageEditor.zoom.value = initialScale

    val screenshotState = rememberScreenshotState()
    imageEditor.screenshotCallback = { screenshotState.capture() }

    if (canvasSize.width > 0 && canvasSize.height > 0) {
        ScreenshotBox(screenshotState, canvasSize, EncodedImageFormat.PNG) {
            Canvas(modifier = Modifier.size(canvasSize.width.pxToDp(), canvasSize.height.pxToDp()).scale(imageEditor.zoom.value)) {

                if (image != null) {
                    drawImage(image, Offset((size.width - image.width) / 2F, (size.height - image.height) / 2F))
                    drawLine(Color.Red, Offset(0F, 0F), Offset(size.width, size.height), strokeWidth = 5F)

                    imageEditor.setImageSize(IntSize(image.width, image.height))
                } else {
                    drawRect(Color.White)
                    drawLine(Color.LightGray, Offset(0F, 0F), Offset(size.width, size.height))

                    imageEditor.setImageSize(size.toIntSize())
                }

            }
        }
    }
}

fun Size.toIntSize() = IntSize(width.toInt(), height.toInt())