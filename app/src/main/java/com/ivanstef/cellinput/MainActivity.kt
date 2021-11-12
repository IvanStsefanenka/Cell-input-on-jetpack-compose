package com.ivanstef.cellinput

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivanstef.cellinput.foundation.actionSecondary
import com.ivanstef.cellinput.foundation.content
import com.ivanstef.cellinput.widget.input.SegmentedTextInput

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = { MainTopBar() }
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SegmentedTextInput(
            cellsAmount = 6,
            modifier = Modifier.padding(horizontal = 12.dp),
            unfocusedBackgroundColor = actionSecondary,
            cellColors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = content,
                textColor = content,
                focusedBorderColor = content,
                unfocusedBorderColor = actionSecondary,
            ),
            onFilled = {
                Log.d("CODE", "Result: $it")
            }
        )
    }
}

@Composable
fun MainTopBar(){
    TopAppBar {
        Text(text = "Segmented input")
    }
}