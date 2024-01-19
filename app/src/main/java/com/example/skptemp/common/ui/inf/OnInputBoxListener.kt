package com.example.skptemp.common.ui.inf

interface OnInputRegexListener {
    fun onChangeCorrect(isCorrect: Boolean, isEmpty: Boolean)
}

interface OnInputFocusListener {
    fun onChangeFocus(hasFocus: Boolean, isEmpty: Boolean)
}