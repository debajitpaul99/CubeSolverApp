package com.debajit.cubesolver.userinterface.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.debajit.cubesolver.camera.CameraPreview
import com.debajit.cubesolver.camera.CubeOverlay
import com.debajit.cubesolver.vision.bitmap.BitmapAnalyzer
import java.io.File
import com.debajit.cubesolver.model.CubeScanSession
import com.debajit.cubesolver.vision.CubeColorReference


@Composable
fun CameraScreen(
    onScanFinished: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    LiveCameraContent(

        modifier = modifier,

        imageCapture = imageCapture,

        onScanFinished = onScanFinished,

        onImageCaptured = { savedPath ->

            CubeScanSession.capturedImagePath = savedPath

            val bitmap = BitmapFactory.decodeFile(savedPath)

            if (bitmap == null) {

                Toast.makeText(
                    context,
                    "Could not read image",
                    Toast.LENGTH_SHORT
                ).show()

                return@LiveCameraContent

            }

            val result = BitmapAnalyzer.analyze(bitmap)

            CubeScanSession.editingColors =
                result.colors.toMutableList()

            CubeColorReference.save(
                CubeScanSession.currentFace,
                result.centerHsv
            )

            onScanFinished()

        }

    )

}
@Composable
private fun LiveCameraContent(
    imageCapture: ImageCapture,
    onImageCaptured: (String) -> Unit,
    onScanFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Step ${CubeScanSession.currentStep} of 6",
                color = Color.LightGray
            )

            Text(
                text = "Scan ${CubeScanSession.currentFace} Face",
                color = Color.White
            )

            Text(
                text = CubeScanSession.scanInstruction(),
                color = Color.Yellow,
                modifier = Modifier.padding(top = 8.dp)
            )

        }

        CameraPreview(

            imageCapture = imageCapture,

            modifier = Modifier.fillMaxSize()

        )

        CubeOverlay(
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Face ${CubeScanSession.currentStep} of 6",
                color = Color.White
            )

            Text(
                text = CubeScanSession.scanInstruction(),
                color = Color.Yellow
            )
        }

        Button(
            onClick = {

                captureCubeFace(

                    imageCapture = imageCapture,

                    context = context,

                    onImageCaptured = onImageCaptured

                )

            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.8f)
                .padding(bottom = 40.dp)
        ) {
            Text("Scan Face")
        }
    }
}

private fun captureCubeFace(
    imageCapture: ImageCapture,
    context: Context,
    onImageCaptured: (String) -> Unit
) {
    val photoFile = File(
        context.cacheDir,
        "cube_face_${System.currentTimeMillis()}.jpg"
    )

    val outputOptions =
        ImageCapture.OutputFileOptions.Builder(photoFile)
            .build()

    Toast.makeText(
        context,
        "Capturing...",
        Toast.LENGTH_SHORT
    ).show()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {

            override fun onImageSaved(
                outputFileResults: ImageCapture.OutputFileResults
            ) {
                onImageCaptured(photoFile.absolutePath)
            }

            override fun onError(
                exception: ImageCaptureException
            ) {
                Toast.makeText(
                    context,
                    "Capture failed: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()

                exception.printStackTrace()
            }
        }
    )
}