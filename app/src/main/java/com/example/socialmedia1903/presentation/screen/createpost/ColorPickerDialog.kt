package com.example.socialmedia1903.presentation.screen.createpost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.presentation.component.BaseButton
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPickerDialog(
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    var selectedColor by remember {
        mutableStateOf(Color.Red)
    }

    val controller = rememberColorPickerController()

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.onSurface,
        title = {

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.pick_color),
                    color = Color.White,
                    fontFamily = fontOpenSansBoldHelper(),
                    modifier = Modifier.align(Alignment.CenterStart),
                )

                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(40.dp)
                        .background(selectedColor, shape = CircleShape)
                        .align(Alignment.CenterEnd)
                )
            }

        },
        text = {
            Column {
                HsvColorPicker(
                    modifier = Modifier.size(300.dp),
                    controller = controller,
                    onColorChanged = {
                        selectedColor = it.color
                    }
                )
            }
        },
        confirmButton = {
            BaseButton(
              modifier = Modifier,
                title = R.string.ok,
                onClick = {
                    onColorSelected(selectedColor)
                    onDismiss()
                }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.White,
                    fontFamily = fontOpenSansBoldHelper()
                )
            }
        }
    )
}