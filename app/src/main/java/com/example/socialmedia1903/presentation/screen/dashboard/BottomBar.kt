package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.socialmedia1903.R

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: Int,
    val selectedIcon: Int
)

@Composable
fun CustomBottomBarWithFab(navController: NavController, onClick: () -> Unit) {

    val items = listOf(
        BottomNavItem("home", "Home", R.drawable.home, R.drawable.home_select),
        BottomNavItem("features", "Features", R.drawable.home_select, R.drawable.home),
        BottomNavItem("notification", "Notify", R.drawable.notification, R.drawable.notification_select),
        BottomNavItem("my-profile", "My Profile", R.drawable.profile, R.drawable.profile_select)
    )

    var selectedItem by remember { mutableStateOf("home") }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedItem = item.route
                            navController.navigate(item.route)
                        },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = if (selectedItem == item.route) item.selectedIcon else item.icon),
                        contentDescription = item.label,
                        tint = colorResource(R.color.base),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = {
               onClick()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
                .clip(
                    shape = CircleShape
                ),
            containerColor = colorResource(id = R.color.base),

        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }
}