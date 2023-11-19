package imageeditor

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.IntSize
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.skia.Image
import org.jetbrains.skiko.loadBytesFromPath
import java.io.File
import java.io.FileOutputStream

sealed class ImageInstanceState {
    object Idle : ImageInstanceState()
    object Loading : ImageInstanceState()
    class Done(val image: ImageBitmap) : ImageInstanceState()
    object Error : ImageInstanceState()
}

class ImageEditor {

    private val _uiState = MutableStateFlow<ImageInstanceState>(ImageInstanceState.Idle)
    val uiState: Flow<ImageInstanceState>
        get() = _uiState

    var screenshotCallback: (() -> ByteArray?)? = null

    var zoom = mutableFloatStateOf(1F)
    var imageSize = mutableStateOf(IntSize.Zero)

    fun zoomIn() {
        zoom.value += ZOOM_STEP
    }

    fun zoomOut() {
        zoom.value -= ZOOM_STEP
    }

    fun setImageSize(size: IntSize) {
        imageSize.value = size
    }

    fun saveFile(directory: String?, fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val bytes = screenshotCallback?.invoke()

            FileOutputStream(File(directory, fileName)).use {
                it.write(bytes)
            }
        }
    }

    fun loadFile(file: MPFile<Any>?) {
        file?.path?.let { imagePath ->
            _uiState.value = ImageInstanceState.Loading
            CoroutineScope(Dispatchers.IO).launch {

                val image = Image
                    .makeFromEncoded(loadBytesFromPath(imagePath))
                    .toComposeImageBitmap()

                withContext(Dispatchers.Main) {
                    _uiState.value = ImageInstanceState.Done(image)
                }
            }
        }
    }

    companion object {
        val SUPPORTED_EXTENSIONS = listOf("jpg", "jpeg", "png", "bmp")
        val ZOOM_STEP = 0.1F
    }

}
