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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    logInViewModel: LogInViewModel = hiltViewModel(),
    navController: NavController,
    paddingValues: PaddingValues
) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by logInViewModel.error.collectAsState()

    val loading by logInViewModel.loading.collectAsState()
    val InterFont = FontFamily(
        Font(R.font.playwriteie_variablefont_wght)
    )

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

            // 👉 gọi ViewModel
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
            .padding(paddingValues)
            .fillMaxSize()
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .background(Color.White),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Đăng nhập",
                fontSize = 28.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            InputComponent(
                placeholder = "Tên đăng nhập",
                onValueChange = { name = it },
                icon = R.drawable.username_login,
                error = error.errName
            )

            InputComponent(
                placeholder = "Mật khẩu",
                onValueChange = { password = it },
                isPwd = true,
                icon = R.drawable.pwd_login,
                error = error.errPassword
            )

            Button(
                onClick = {
                    logInViewModel.logIn(name, password)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFd10058),
                )
            ) {
                Text("Đăng nhập")
            }
            Text(
                text = "Tạo tài khoản",
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate("signup")
                    }
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
                text = "Others",
                fontSize = 14.sp,
                color = Color.Black,
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