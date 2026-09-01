package com.debajit.cubesolver.vision

import androidx.camera.core.ImageProxy
import com.debajit.cubesolver.model.CubeColor
import com.debajit.cubesolver.camera.GridCalculator

object FaceScanner {

    fun scanFace(
        image: ImageProxy
    ): List<CubeColor> {

        val gridSize = minOf(image.width, image.height) * 0.60f

        val left = (image.width - gridSize) / 2f
        val top = (image.height - gridSize) / 2f
        val cell = gridSize / 3f

        val points = GridCalculator.calculateGrid(
            left,
            top,
            cell
        )

        val colors = mutableListOf<CubeColor>()

        for (point in points) {

            val rgb = YuvConverter.getAverageRgb(
                image,
                point.position.x.toInt(),
                point.position.y.toInt(),
                8
            )

            val hsv = ColorConverter.rgbToHsv(rgb)

            android.util.Log.d(
                "HSV",
                "H=${hsv.hue} S=${hsv.saturation} V=${hsv.value}"
            )

            val cubeColor = ColorClassifier.classify(hsv)

            colors.add(cubeColor)

        }

        return colors

    }

}