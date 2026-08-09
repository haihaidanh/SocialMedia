package com.example.socialmedia1903.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansSemiBoldHelper
import com.example.socialmedia1903.presentation.theme.baseColor

@Composable
fun BaseButton(
    modifier: Modifier,
    title: Int,
    onClick: () -> Unit,
    textSize: Int = 16,
    radius: Int = 1000,
    paddingHorizontal: Int = 16,
    paddingVertical: Int = 8,
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(radius.dp)
            )
            .clickable {
                onClick()
            }
            .background(
                color = baseColor,
                shape = RoundedCornerShape(radius.dp)
            )
    ) {

        Text(
            text = stringResource(id = title),
            color = Color.White,
            fontSize = textSize.sp,
            modifier = Modifier
                .padding(
                    horizontal = paddingHorizontal.dp,
                    vertical = paddingVertical.dp
                )
                .align(Alignment.Center),
            fontFamily = fontOpenSansSemiBoldHelper()
        )
    }
}