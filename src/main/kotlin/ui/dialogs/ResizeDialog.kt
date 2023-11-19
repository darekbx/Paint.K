package ui.dialogs

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ui.PaintDotKTheme
import kotlin.math.min

/**
 * - additionally: calculate result size??
 */
@Composable
fun ResizeDialog(
    originalSize: IntSize,
    onResize: (IntSize) -> Unit = { },
    onCancel: () -> Unit = { },
    maxValue: Int = 10000
) {
    DialogBox(modifier = Modifier.size(400.dp, 400.dp).background(Color.White)) {

        Column(Modifier.fillMaxWidth().padding(16.dp)) {

            var isAspectRatioLocked by remember { mutableStateOf(true) }
            val initialRatio = originalSize.width.toDouble() / originalSize.height.toDouble()
            var width by remember { mutableIntStateOf(originalSize.width) }
            var height by remember { mutableIntStateOf(originalSize.height) }

            DialogTitle("Resize image")

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1F), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RowLabel("Width: ")
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "$width",
                            singleLine = true,
                            trailingIcon = { TrailingPx() },
                            textStyle = TextStyle(textAlign = TextAlign.End),
                            onValueChange = {
                                width = restrictToDigits(it, maxValue)
                                if (isAspectRatioLocked) {
                                    height = (width / initialRatio).toInt()
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            shape = RoundedCornerShape(4.dp),
                            colors = dialogTextFieldColors()
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RowLabel("Height: ")
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "$height",
                            singleLine = true,
                            trailingIcon = { TrailingPx() },
                            textStyle = TextStyle(textAlign = TextAlign.End),
                            onValueChange = {
                                height = restrictToDigits(it, maxValue)
                                if (isAspectRatioLocked) {
                                    height = (width / initialRatio).toInt()
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            shape = RoundedCornerShape(4.dp),
                            colors = dialogTextFieldColors()
                        )
                    }
                }
                DrawLock { isAspectRatioLocked = it }
            }

            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                RowLabel("Ratio: ")
                Text(
                    modifier = Modifier
                        .padding(end = 48.dp)
                        .fillMaxWidth()
                        .background(PaintDotKTheme.colors.material.surface, RoundedCornerShape(4.dp))
                        .padding(16.dp),
                    color = Color.LightGray,
                    textAlign = TextAlign.End,
                    text = "%.2f".format(width.toDouble() / height.toDouble())
                )
            }

            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                RowLabel("Image size: ")
                Text("TODO", color = Color.Red)
            }


            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.End) {
                Button(onClick = onCancel) {
                    Text("Cancel", color = PaintDotKTheme.colors.material.onSurface)
                }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { onResize(IntSize(width, height)) }) {
                    Text("Resize", color = PaintDotKTheme.colors.material.onSurface)
                }
            }
        }
    }
}

private fun restrictToDigits(it: String, maxValue: Int): Int {
    val pattern = Regex("^\\d+\$")
    return if (it.matches(pattern)) {
        min(it.toInt(), maxValue)
    } else {
        0
    }
}

@Composable
private fun TrailingPx() {
    Text(
        modifier = Modifier.padding(end = 8.dp, start = 0.dp),
        text = "px",
        fontWeight = FontWeight.Light,
        color = Color.LightGray
    )
}

@Composable
private fun DrawLock(onLockClick: (Boolean) -> Unit = { }) {
    var isLocked by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .padding(start = 8.dp)
            .clickable {
                isLocked = !isLocked
                onLockClick(isLocked)
            }, verticalArrangement = Arrangement.Center
    ) {
        val thickness = if (isLocked) 4F else 2F
        Canvas(modifier = Modifier.padding(bottom = 4.dp).size(width = 40.dp, height = 20.dp)) {
            drawLine(
                Color.Black,
                Offset(0F, 0F),
                Offset(size.width / 2F, 0F),
                strokeWidth = thickness
            )
            drawLine(
                Color.Black,
                Offset(size.width / 2F - 1F, 0F),
                Offset(size.width / 2F - 1F, size.height),
                strokeWidth = thickness
            )
        }
        Icon(
            modifier = Modifier.width(40.dp),
            imageVector = if (isLocked) Icons.Default.Lock else Icons.Default.LockOpen,
            contentDescription = "lock_aspect_ratio",
            tint = PaintDotKTheme.colors.material.background
        )
        Canvas(modifier = Modifier.padding(top = 4.dp).size(width = 40.dp, height = 20.dp)) {
            drawLine(
                Color.Black,
                Offset(0F, size.height),
                Offset(size.width / 2F, size.height),
                strokeWidth = thickness
            )
            drawLine(
                Color.Black,
                Offset(size.width / 2F - 1F, 0F),
                Offset(size.width / 2F - 1F, size.height),
                strokeWidth = thickness
            )
        }
    }
}

@Preview
@Composable
fun ResizeDialogPreview() {
    MaterialTheme(colors = PaintDotKTheme.colors.material) {
        Surface(modifier = Modifier) {
            ResizeDialog(IntSize(1024, 748))
        }
    }
}