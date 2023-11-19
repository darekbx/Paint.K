package ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.PaintDotKTheme

@Composable
fun DialogBox(modifier: Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7F)),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = modifier.background(Color.White)) {
            content()
        }
    }
}

@Composable
fun DialogTitle(title: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            color = PaintDotKTheme.colors.material.background,
            fontSize = 18.sp,
            text = title
        )
    }
}

@Composable
fun RowLabel(label: String) {
    Text(
        modifier = Modifier.width(110.dp),
        color = PaintDotKTheme.colors.material.background,
        text = label
    )
}

@Composable
fun dialogTextFieldColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = PaintDotKTheme.colors.material.surface,
    cursorColor = Color.White,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
)