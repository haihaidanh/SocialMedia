package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun CustomBottomBarWithFab(navController: NavController, onClick: () -> Unit) {

    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("group", "Group", Icons.Default.Warning),
        BottomNavItem("notification", "Notify", Icons.Default.Notifications),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    Box {
        // Bottom bar nền
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEachIndexed { index, item ->

                // Chừa chỗ ở giữa cho FAB
                if (index == 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate(item.route)
                        },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                }
            }
        }

        // FAB ở giữa (lún xuống)
        FloatingActionButton(
            onClick = {
               onClick()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp) // chỉnh độ lún ở đây
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}