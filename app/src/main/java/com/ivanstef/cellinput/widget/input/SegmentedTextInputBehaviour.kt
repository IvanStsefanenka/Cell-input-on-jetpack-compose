package com.ivanstef.cellinput.widget.input

import android.util.Log

interface SegmentedTextInputBehaviour {
    fun onValueInCellChanged(index: Int, newValue: String)
}

internal class DefaultSegmentedTextInputBehaviour(
    private val cellsAmount: Int,
    private val onFilled: (code: String) -> Unit
) : SegmentedTextInputBehaviour {

    private var filledCells = 0
    private val inputBuilder = CharArray(cellsAmount)

    override fun onValueInCellChanged(index: Int, newValue: String) {
        Log.d("CODE", "newValue: $newValue")
        if (newValue.isNotEmpty()) {
            val charValue = newValue[0]
            inputBuilder[index] = charValue

            if (charValue.isDigit()) {
                filledCells++
            }
            if (filledCells == cellsAmount) {
                onFilled.invoke(inputBuilder.concatToString())
                Log.d("CODE", "result: ${inputBuilder.concatToString()}")
            }
        } else {
            if (inputBuilder[index].isDigit()) {
                filledCells--
            }
            inputBuilder[index] = '\u0000'
        }
    }
}