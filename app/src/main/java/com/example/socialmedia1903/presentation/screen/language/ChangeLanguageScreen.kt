package com.example.socialmedia1903.presentation.screen.language

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.screen.setting.SettingHelper.listLanguage
import com.example.socialmedia1903.presentation.screen.setting.SettingsViewModel
import com.example.socialmedia1903.presentation.theme.baseColor

@Composable
fun ChangeLanguageScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val currentLanguage by settingsViewModel.language.collectAsState()

    var selectedLanguageCode by remember(currentLanguage) {
        mutableStateOf(currentLanguage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderSelectLanguage(
                title = stringResource(R.string.select_language),
                modifier = Modifier
                    .staticStatusBarPadding(),
                onBackClick = { navController.popBackStack() },
                onChangeLanguage = {
                    settingsViewModel.changeLanguage(selectedLanguageCode)
                    navController.popBackStack()
                },
                saveVisible = selectedLanguageCode != currentLanguage
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(listLanguage()) { language ->
                    val isSelected = language.code == selectedLanguageCode
                    LanguageItem(
                        language = language,
                        isSelected = isSelected,
                        onClick = {
                            selectedLanguageCode = language.code
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSelectLanguage(
    onBackClick: () -> Unit,
    title: String,
    modifier: Modifier,
    onChangeLanguage: () -> Unit,
    saveVisible: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(18.dp)
                .clickable {
                    onBackClick()
                },
            tint = MaterialTheme.colorScheme.onSurface
        )


        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = fontOpenSansBoldHelper()
        )

        if (saveVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clip(
                        RoundedCornerShape(1000.dp)
                    )
                    .clickable {
                        onChangeLanguage()
                    }
                    .background(
                        color = baseColor,
                        RoundedCornerShape(1000.dp)
                    )
            ) {
                Text(
                    text = stringResource(R.string.save),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontFamily = fontOpenSansMediumHelper(),
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }

    }
}