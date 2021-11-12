package com.ivanstef.cellinput.widget.input

import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SegmentedTextInput(
    cellsAmount: Int,
    cellColors: TextFieldColors,
    onFilled: (code: String) -> Unit,
    unfocusedBackgroundColor: Color,
    modifier: Modifier = Modifier,
    cellsInputBehaviour: SegmentedTextInputBehaviour =
        remember { DefaultSegmentedTextInputBehaviour(cellsAmount, onFilled) }
) {
    SegmentedTextInputImpl(
        cellsAmount = cellsAmount,
        cellColors = cellColors,
        modifier = modifier,
        cellsInputBehaviour = cellsInputBehaviour,
        unfocusedBackgroundColor = unfocusedBackgroundColor
    )
}