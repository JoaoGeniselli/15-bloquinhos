package com.dosei.game.blocks.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.dosei.game.blocks.R
import com.dosei.game.blocks.ui.component.ResetIcon
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(onReset: () -> Unit) {
    val scope = rememberCoroutineScope()
    val helpState = remember { RichTooltipState() }
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = onReset) {
                ResetIcon()
            }
            RichTooltipBox(
                modifier = Modifier,
                tooltipState = helpState,
                title = { Text(stringResource(R.string.help)) },
                text = { Text(stringResource(R.string.gameplay_help)) },
                action = {
                    Button(
                        onClick = { scope.launch { helpState.dismiss() } },
                        content = { Text(text = stringResource(R.string.understood)) }
                    )
                }
            ) {
                IconButton(onClick = { scope.launch { helpState.show() } }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_help_outline_24),
                        contentDescription = stringResource(R.string.help)
                    )
                }
            }
        }
    )
}