package com.example.socialmedia1903.presentation.screen.group

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.presentation.component.Header
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding


@Composable
fun GroupListScreen(
    groupViewModel: GroupViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        groupViewModel.getAllGroup()
    }

    val groups by groupViewModel.groups.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        Header(
            title = stringResource(id = R.string.my_groups),
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            onBackClick = {
                navController.popBackStack()
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(groups) { group ->
                GroupItemView(group = group) {
                    navController.navigate("group/${group.id}")
                }
            }
        }
    }
}


@Composable
fun GroupItemView(
    group: GroupInfo,
    onClick: () -> Unit = { }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = group.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = group.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = fontOpenSansMediumHelper()
        )
    }
}
