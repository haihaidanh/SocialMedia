package com.example.socialmedia1903.presentation.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.domain.enums.ThemeMode
import com.example.socialmedia1903.presentation.component.Header
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navController: NavController
) {
    val themeMode by viewModel.theme.collectAsStateWithLifecycle()

    val isDarkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Header(
            title = stringResource(R.string.settings),
            onBackClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding()
        )

        Row(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .clickable {
                    navController.navigate("change_language")
                }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.language),
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }



        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Chế độ tối", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = if (isDarkTheme) "Đang bật" else "Đang tắt",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { checked ->
                        viewModel.changeTheme(
                            if (checked) ThemeMode.DARK else ThemeMode.LIGHT
                        )
                    },
                    thumbContent = {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.ArrowBack else Icons.Default.List,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )
            }
        }
    }
}


