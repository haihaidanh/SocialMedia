package com.example.socialmedia1903.presentation.screen.signup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.presentation.component.BaseButton
import com.example.socialmedia1903.presentation.component.Header
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.screen.login.AuthInput

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    var selectedGender by remember { mutableIntStateOf(R.string.male) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val isSignUpSuccess by signUpViewModel.isSignUpSuccess.collectAsState()
    val context = LocalContext.current

    val err by signUpViewModel.error.collectAsState()

    if (isSignUpSuccess) {
        navController.navigate("login")
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Header(
            title = stringResource(R.string.sign_up),
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding(),
            onBackClick = {
                navController.popBackStack()
            }
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
        }

        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.name),
            onSearchQueryChange = { name = it },
            icon = R.drawable.people_icon,
            searchQuery = name,
        )

        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.username),
            onSearchQueryChange = { username = it },
            icon = R.drawable.people_icon,
            searchQuery = username,
        )

        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.password),
            onSearchQueryChange = { password = it },
            icon = R.drawable.pwd_login,
            searchQuery = password,
            password = true
        )

        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.confirm_password),
            onSearchQueryChange = { confirmPassword = it },
            icon = R.drawable.pwd_login,
            searchQuery = confirmPassword,
            password = true
        )

        Text(
            text = stringResource(R.string.gender),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = fontOpenSansMediumHelper(),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedGender == R.string.male,
                    onClick = { selectedGender = R.string.male }
                )
                Text(
                    text = stringResource(R.string.male),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = fontOpenSansMediumHelper(),
                    fontSize = 12.sp
                    )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedGender == R.string.female,
                    onClick = { selectedGender = R.string.female }
                )
                Text(
                    text = stringResource(R.string.female),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = fontOpenSansMediumHelper(),
                    fontSize = 12.sp
                )
            }
        }

        BaseButton(
            title = R.string.sign_up,
            onClick = {
                signUpViewModel.signUp(
                    imageUri ?: Uri.EMPTY,
                    username,
                    name,
                    password,
                    confirmPassword,
                    if (selectedGender == R.string.male) 1 else 2,
                    context
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}
