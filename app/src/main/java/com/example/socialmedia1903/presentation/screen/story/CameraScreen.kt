package com.example.socialmedia1903.presentation.screen.story

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import java.io.File

@Composable
fun CameraScreen(
    onVideoRecorded: (Uri) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }

    var recording by remember { mutableStateOf<Recording?>(null) }
    var isRecording by remember { mutableStateOf(false) }

    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] == true
        val audioGranted = permissions[Manifest.permission.RECORD_AUDIO] == true

        hasPermission = cameraGranted && audioGranted
    }

    LaunchedEffect(Unit) {
        val cameraGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val audioGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (cameraGranted && audioGranted) {
            hasPermission = true
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
                )
            )
        }
    }

    if (!hasPermission) {
        Box(
            Modifier.fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.camera_requiring),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = fontOpenSansMediumHelper()
            )
        }
        return
    }

    LaunchedEffect(Unit) {
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val recorder = Recorder.Builder().build()
        val videoCapture = VideoCapture.withOutput(recorder)

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            videoCapture
        )

        previewView.tag = videoCapture
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
        if(!isRecording){
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.TopStart)
                    .staticStatusBarPadding()
                    .padding(horizontal = 10.dp)
                    .clickable { navController.popBackStack() }
            )
        }

        PauseButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-24).dp)
                .padding(horizontal = 16.dp),
            isRecording = isRecording,
            onClick = {
                isRecording = !isRecording
            }
        )
    }
}

@Composable
fun PauseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isRecording: Boolean
){
    val scaleAnimation by animateFloatAsState(
        targetValue = if (isRecording) 1.2f else 1.0f,
        animationSpec = tween(durationMillis = 300),
        label = "scaleAnimation"
    )

    val doneAnimation by animateFloatAsState(
        targetValue = if (isRecording) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "scaleAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scaleAnimation
                    scaleY = scaleAnimation
                }
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    Color.White
                )
                .clickable {
                    onClick()
                }
                .align(Alignment.Center)
        )

        if(isRecording){
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(
                        RoundedCornerShape(4.dp)
                    )
                    .background(
                        Color.Red
                    )
                    .align(Alignment.Center)
            )
            Icon(
                painter = painterResource(R.drawable.tick_on),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .alpha(doneAnimation)
                    .align(Alignment.CenterStart)
            )
        }else{
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        Color.Red
                    )
                    .align(Alignment.Center)
            )
        }

        Icon(
            painter = painterResource(R.drawable.swwitch),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
        )
    }
}