package com.debajit.cubesolver.userinterface.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.debajit.cubesolver.camera.CameraPreview
import com.debajit.cubesolver.camera.CubeOverlay
import java.io.File
import com.debajit.cubesolver.vision.bitmap.BitmapAnalyzer
import com.debajit.cubesolver.model.CubeState
import com.debajit.cubesolver.model.ScanSession
import com.debajit.cubesolver.vision.CalibrationManager
import com.debajit.cubesolver.model.FaceStorage
import com.debajit.cubesolver.vision.cube.CubeAnalyzer
import com.debajit.cubesolver.vision.opencv.OpenCvTest

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    android.util.Log.d(
        "OpenCV",
        OpenCvTest.version()
    )

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    var capturedImagePath by remember {
        mutableStateOf<String?>(null)
    }

    if (capturedImagePath == null) {
        LiveCameraContent(
            modifier = modifier,
            imageCapture = imageCapture,
            onImageCaptured = { savedPath ->
                capturedImagePath = savedPath
            }
        )
    } else {
        CapturedFaceReview(
            modifier = modifier,
            imagePath = capturedImagePath!!,
            onRetake = {
                capturedImagePath = null
            },
            onConfirm = {

                val bitmap = BitmapFactory.decodeFile(capturedImagePath)

                if (bitmap == null) {
                    Toast.makeText(
                        context,
                        "Could not read image",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@CapturedFaceReview
                }

                val result = BitmapAnalyzer.analyze(bitmap)


                val scannedFace = ScanSession.currentFace()

                CalibrationManager.saveReference(
                    scannedFace,
                    result.centerHsv
                )

                CubeState.saveFace(
                    scannedFace,
                    result.colors
                )

                FaceStorage.saveImage(
                    scannedFace,
                    capturedImagePath!!
                )

                val completed = CalibrationManager.calibratedFaces()

                if (!ScanSession.isFinished()) {
                    ScanSession.nextFace()
                }

                if (ScanSession.isFinished()) {

                    val cube =
                        CubeAnalyzer.analyzeCube()

                    Toast.makeText(
                        context,
                        "Cube analyzed (${cube.size} faces)",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        context,
                        "Saved $scannedFace ($completed/6)",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                capturedImagePath = null

            }
        )
    }
}

@Composable
private fun LiveCameraContent(
    imageCapture: ImageCapture,
    onImageCaptured: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            text = "Scan ${ScanSession.currentFace()} Face",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
        )
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
                text = "Face ${ScanSession.currentStep()} of 6",
                color = Color.White
            )

            Text(
                text = "Center the ${ScanSession.currentFace()} face",
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
            Text("Capture Face")
        }
    }
}

@Composable
private fun CapturedFaceReview(
    imagePath: String,
    onRetake: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(imagePath) {
        BitmapFactory.decodeFile(imagePath)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Review Captured Face",
            color = Color.White
        )

        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured cube face",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(
                text = "Could not load captured image",
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onRetake,
                modifier = Modifier.weight(1f)
            ) {
                Text("Retake")
            }

            Button(
                onClick = onConfirm,
                modifier = Modifier.weight(1f)
            ) {
                Text("Confirm")
            }
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