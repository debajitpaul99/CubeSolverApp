package com.debajit.cubesolver.camera.analysis

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.debajit.cubesolver.vision.FaceScanner
import com.debajit.cubesolver.vision.GridCalculator

class FrameAnalyzer : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {

        val gridSize = minOf(image.width, image.height) * 0.60f

        val left = (image.width - gridSize) / 2f
        val top = (image.height - gridSize) / 2f
        val cell = gridSize / 3f

        val points = GridCalculator.calculateGrid(
            left,
            top,
            cell
        )

        val colors = FaceScanner.scanFace(
            image,
            points
        )

        Log.d(
            "CubeScanner",
            colors.joinToString()
        )

        image.close()
    }
}