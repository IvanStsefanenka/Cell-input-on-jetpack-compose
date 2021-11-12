package com.ivanstef.cellinput.widget.input

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivanstef.cellinput.foundation.contentDisabled

@Composable
internal fun SegmentedTextInputImpl(
    cellsAmount: Int,
    cellColors: TextFieldColors,
    unfocusedBackgroundColor: Color,
    modifier: Modifier,
    cellsInputBehaviour: SegmentedTextInputBehaviour,
) {
    val focusRequesters: List<FocusRequester> = remember {
        List(cellsAmount) { FocusRequester() }
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 0 until cellsAmount) {
            TextInputCell(
                index = index,
                unfocusedBackgroundColor = unfocusedBackgroundColor,
                textFieldColors = cellColors,
                focusRequester = focusRequesters[index],
                prevRequester = focusRequesters.getOrNull(index - 1),
                nexRequester = focusRequesters.getOrNull(index + 1),
                onValueChanged = cellsInputBehaviour::onValueInCellChanged
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TextInputCell(
    index: Int,
    textFieldColors: TextFieldColors,
    unfocusedBackgroundColor: Color,
    focusRequester: FocusRequester,
    prevRequester: FocusRequester? = null,
    nexRequester: FocusRequester? = null,
    onValueChanged: (Int, String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isCellFocused by remember { mutableStateOf(false) }
    val inputService = LocalTextInputService.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .width(56.dp)
            .height(60.dp)
            .focusRequester(focusRequester)
            .background(
                color = if (isCellFocused)
                    Color.Transparent
                else
                    unfocusedBackgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .onFocusChanged {
                Log.d("focuschanged:", "$index focus ${it.isFocused}")
                isCellFocused = it.isFocused
            },
        textStyle = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
        value = text,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(4.dp),
        singleLine = true,
        maxLines = 1,
        placeholder = {
            if (!isCellFocused) {
                CirclePlaceholder()
            }
        },
        colors = textFieldColors,
        onValueChange = { newValue ->
            if (focusRequester.freeFocus()) {
                Log.d("CODE", "$index valueChanged: $newValue")
                when (newValue.length) {
                    1 -> {
                        Log.d("($index)ValueChanged1", newValue)
                        text = newValue
                        onValueChanged.invoke(index, newValue)
                        nexRequester?.requestFocus() ?: run {
                            focusManager.clearFocus()
                            inputService?.hideSoftwareKeyboard()
                        }
                    }
                    0 -> {
                        Log.d("($index)ValueChanged2", "newValue = $newValue")
                        text = newValue
                        onValueChanged.invoke(index, newValue)
                        prevRequester?.requestFocus()
                    }
                }
            }
        }
    )
}

@Composable
private fun CirclePlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(8.dp)
                .background(
                    color = contentDisabled,
                    shape = CircleShape
                )
        )
    }
}