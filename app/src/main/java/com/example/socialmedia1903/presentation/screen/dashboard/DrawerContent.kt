package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding

@Composable
fun DrawerItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.edit),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 10.dp)
                .size(12.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
            ,
            fontSize = 16.sp,
            fontFamily = fontOpenSansMediumHelper(),
            color = MaterialTheme.colorScheme.onSurface
        )
    }


}

@Composable
fun DrawerContent(
    avatar: String,
    username: String?,
    onCreateGroup: () -> Unit,
    onMyGroups: () -> Unit,
    onSetting: () -> Unit,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(bottomEnd = 30.dp, topEnd = 30.dp)
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding()
                .padding(horizontal = 5.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = avatar,
                contentDescription = "hai",
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .size(30.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = username ?: "hai",
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontOpenSansBoldHelper(),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            DrawerItem(
                title = stringResource(id = R.string.create_group),
                onClick = onCreateGroup,
                modifier = Modifier
            )

            DrawerItem(
                title = stringResource(id = R.string.my_groups),
                onClick = onMyGroups,
                modifier = Modifier
            )

            DrawerItem(
                title = stringResource(id = R.string.setting),
                onClick = onSetting,
                modifier = Modifier
            )

            DrawerItem(
                title = stringResource(id = R.string.logout),
                onClick = {
                    onLogout()
                },
                modifier = Modifier
            )
        }

    }
}
