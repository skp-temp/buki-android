package com.example.skptemp.feature.signup.data

import kotlinx.coroutines.flow.MutableStateFlow

data class Terms(
    var isChecked: MutableStateFlow<Boolean>,
    val title: String,
    val isRequired: Boolean,
    val landingUrl: String
)