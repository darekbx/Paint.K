@file:OptIn(ExperimentalFoundationApi::class)

package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import commands.Command

@Composable
fun CommandBar(modifier: Modifier, onCommandClicked: (Command) -> Unit) {
    LazyRow(modifier = modifier) {
        items(Command.entries) { command ->
            if (command.isDivider) {
                Divider(Modifier.padding(top = 4.dp).height(40.dp).width(1.dp))
            }else {
                TooltipArea(tooltip = {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color.DarkGray)
                            .padding(4.dp),
                        text = command.label,
                        fontSize = 11.sp
                    )
                }) {
                    IconButton({ onCommandClicked(command) }) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = command.icon,
                            contentDescription = command.label
                        )
                    }
                }
            }
        }
    }
}