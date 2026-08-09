package com.example.socialmedia1903.presentation.screen.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.presentation.component.BaseButton
import com.example.socialmedia1903.presentation.theme.baseColor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun LoginScreen(
    logInViewModel: LogInViewModel = hiltViewModel(),
    navController: NavController,
) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by logInViewModel.error.collectAsState()

    val loading by logInViewModel.loading.collectAsState()

    val context = LocalContext.current

    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        GoogleSignIn.getClient(context, gso)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result

            logInViewModel.loginWithGoogle(account.idToken!!)
        }
    }

    LaunchedEffect(loading) {
        if (!loading) {
            navController.navigate("home")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(
                baseColor
            ),
    ) {

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .background(Color.White)
                .align(Alignment.Center)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.login),
                fontSize = 24.sp,
                fontFamily = fontOpenSansBoldHelper(),
                fontWeight = FontWeight.Bold,
                color = baseColor
            )

            AuthInput(
                modifier = Modifier.padding(vertical = 8.dp),
                searchQuery = name,
                onSearchQueryChange = { name = it },
                password = false,
                icon = R.drawable.user,
                placeholder = stringResource(R.string.username)
            )

            AuthInput(
                modifier = Modifier.padding(bottom = 8.dp),
                searchQuery = password,
                onSearchQueryChange = { password = it },
                password = true,
                icon = R.drawable.password,
                placeholder = stringResource(R.string.password)
            )

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                title = R.string.login,
                onClick = {
                    logInViewModel.logIn(name, password)
                }
            )
            Text(
                text = stringResource(R.string.sign_up_navigate),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("signup")
                    },
                fontSize = 12.sp,
                color = baseColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.others),
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = fontOpenSansBoldHelper(),
                fontWeight = FontWeight.Bold
            )

            Row {
                Image(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(8.dp)
                        .clickable {

                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(8.dp)
                        .clickable {
                            launcher.launch(googleSignInClient.signInIntent)
                        }
                )
            }
        }
    }
}