package com.example.socialmedia1903.presentation.core.modifier


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

var maximumStatusBarHeight by mutableStateOf(24.dp)

fun Modifier.staticStatusBarPadding(): Modifier = composed {
    val statusBarHeight =
        WindowInsets.statusBars
            .asPaddingValues()
            .calculateTopPadding()

    LaunchedEffect(statusBarHeight) {
        if (statusBarHeight > maximumStatusBarHeight) {
            maximumStatusBarHeight = statusBarHeight
        }
    }

    padding(top = maximumStatusBarHeight)
}