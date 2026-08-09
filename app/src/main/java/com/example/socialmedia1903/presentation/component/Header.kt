package com.example.socialmedia1903.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper

@Composable
fun Header(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
    ) {

        Icon(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .clickable {
                    onBackClick()
                }
                .align(Alignment.CenterStart),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontFamily = fontOpenSansBoldHelper()
        )
    }
}