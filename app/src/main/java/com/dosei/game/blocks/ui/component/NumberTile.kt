package com.dosei.game.blocks.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NumberTile(modifier: Modifier = Modifier, number: Int) {
    Tile(modifier = modifier) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview
@Composable
private fun PreviewNumberTile() {
    NumberTile(
        modifier = Modifier.size(72.dp),
        number = 15
    )
}
