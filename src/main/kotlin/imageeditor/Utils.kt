package imageeditor

import androidx.compose.ui.unit.IntSize

object Utils {

    /**
     * @param scaleOffset Define padding of the scale, 0.1F means 10% padding.
     */
    fun calculateInitialScale(
        imageSize: IntSize,
        parentSize: IntSize,
        scaleOffset: Float = 0.1F
    ): Float {
        if (parentSize.width < imageSize.width || parentSize.height < imageSize.height) {
            val widthRatio = parentSize.width / imageSize.width.toFloat()
            val heightRatio = parentSize.height / imageSize.height.toFloat()
            return minOf(widthRatio, heightRatio) / (1F + scaleOffset)
        }
        return 1F
    }
}