package com.example.socialmedia1903.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia1903.presentation.theme.baseColor

@Composable
fun RoundButton(
    modifier: Modifier,
    title: Int,
    icon: Int? = null,
    onClick: () -> Unit,
    radius: Int = 1000,
    textSize: Int = 12
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(radius.dp)
            )
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = baseColor,
                shape = RoundedCornerShape(radius.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = baseColor
                )
            }
            Text(
                text = stringResource(id = title),
                color = baseColor,
                fontSize = textSize.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}