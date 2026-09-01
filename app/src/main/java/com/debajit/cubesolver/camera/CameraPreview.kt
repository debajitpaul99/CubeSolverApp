package com.debajit.cubesolver.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.camera.core.UseCase

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    imageCapture: ImageCapture? = null
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    DisposableEffect(Unit) {

        val providerFuture =
            ProcessCameraProvider.getInstance(context)

        providerFuture.addListener({

            val provider = providerFuture.get()

            val preview = Preview.Builder().build()

            preview.surfaceProvider =
                previewView.surfaceProvider


            provider.unbindAll()

            val useCases = mutableListOf<UseCase>(preview)

            imageCapture?.let {

                useCases.add(it)

            }

            provider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                *useCases.toTypedArray()
            )

        }, ContextCompat.getMainExecutor(context))

        onDispose {

            if (providerFuture.isDone) {

                providerFuture.get().unbindAll()

            }

        }

    }

    AndroidView(
        factory = { previewView },
        modifier = modifier
    )

}