package com.example.socialmedia1903.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.presentation.theme.baseColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun InputComponent(
    modifier: Modifier = Modifier,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isPwd: Boolean = false,
    icon: Int,
    error: String? = null,

) {
    var text by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
        ),
        label = {
            Text(
                placeholder,
                fontSize = 14.sp,
            )
        },

        modifier = modifier
            .fillMaxWidth(),

        singleLine = true,

        visualTransformation =
            if (isPwd && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        leadingIcon = {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
        },

        trailingIcon = {
            if (isPwd) {
                Icon(
                    painter = if (passwordVisible)
                        painterResource(R.drawable.show)
                    else
                        painterResource(R.drawable.hidden),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            passwordVisible = !passwordVisible
                        }
                        .size(20.dp)

                )
            }
        },

        shape = RoundedCornerShape(30.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            errorBorderColor = Color.Red,
            focusedLabelColor = Color.Blue,
            cursorColor = Color.Blue
        )
    )

    if (error != null) {
        Text(
            text = error,
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Composable
fun AuthInput(
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit = {},
    searchQuery: String,
    password: Boolean = false,
    icon: Int? = null,
    placeholder: String,
    keyboardAction: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var focus by remember { mutableStateOf(false) }

    BasicTextField(
        value = searchQuery,
        onValueChange = {
            onSearchQueryChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .onFocusChanged{
                focus = it.isFocused
            }
            .border(
                width = 1.dp,
                color = if(focus) baseColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            fontFamily = fontOpenSansMediumHelper()
        ),
        visualTransformation = if (password && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardAction()
            }
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    icon?.let {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                fontFamily = fontOpenSansMediumHelper()
                            )
                        }
                        innerTextField()
                    }
                }
                if (password) {
                    Icon(
                        painter = painterResource(
                            if (passwordVisible) R.drawable.show else R.drawable.hidden
                        ),
                        contentDescription = "Password Visibility Icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(20.dp)
                            .clickable {
                                passwordVisible = !passwordVisible
                            }
                    )
                }
            }
        }
    )
}