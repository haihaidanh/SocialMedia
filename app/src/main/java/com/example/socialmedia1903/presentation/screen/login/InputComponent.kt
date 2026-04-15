package com.example.socialmedia1903.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia1903.R

@Composable
fun InputComponent(
    placeholder: String,
    onValueChange: (String) -> Unit,
    isPwd: Boolean = false,
    icon: Int,
    error: String? = null,
    modifier: Modifier = Modifier
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

        // 👇 MẶT NẠ PASSWORD
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