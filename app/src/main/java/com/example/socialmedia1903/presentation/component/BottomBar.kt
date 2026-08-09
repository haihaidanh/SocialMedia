package com.example.socialmedia1903.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.socialmedia1903.R

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: Int,
    val selectedIcon: Int
)

@Composable
fun CustomBottomBarWithFab(
    onAddClick: () -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier,
    selected: String
) {

    val items = listOf(
        BottomNavItem("home", "Home", R.drawable.home, R.drawable.home_selected),
        BottomNavItem("features", "Features", R.drawable.group, R.drawable.group),
        BottomNavItem("notification", "Notify", R.drawable.noti, R.drawable.noti_selected),
        BottomNavItem("my-profile", "My Profile", R.drawable.profile, R.drawable.profile_selected)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                )
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {
                            onItemClick(item.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = if (selected == item.route) item.selectedIcon else item.icon),
                        contentDescription = item.label,
                        tint = colorResource(R.color.base),
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = colorResource(R.color.base),
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Add",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
                .size(56.dp)
                .clip(CircleShape)
                .clickable {
                    onAddClick()
                }
        )
    }
}