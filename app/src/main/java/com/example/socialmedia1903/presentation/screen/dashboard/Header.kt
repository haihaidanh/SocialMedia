package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    drawerOnClick: () -> Unit={},
    searchOnClick: () -> Unit={},
    title: String= "Dashboard",
    //modifier: Modifier
){
    TopAppBar(
        modifier = Modifier,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFd10058)
            )
        },
        navigationIcon = {
            IconButton(onClick = drawerOnClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFFd10058)
                )
            }
        },
        actions = {
            IconButton(onClick = searchOnClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFFd10058)
                )
            }
        }
    )
}
