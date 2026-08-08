package com.debajit.cubesolver.vision

import androidx.camera.core.ImageProxy
import com.debajit.cubesolver.model.CubeColor

object FaceScanner {

    fun scanFace(
        image: ImageProxy,
        points: List<SamplingPoint>
    ): List<CubeColor> {

        val colors = mutableListOf<CubeColor>()

        points.forEach { point ->

            val rgb = YuvConverter.getAverageRgb(
                image = image,
                centerX = point.position.x.toInt(),
                centerY = point.position.y.toInt(),
                radius = 7
            )

            val hsv = ColorConverter.rgbToHsv(rgb)

            val color = ColorClassifier.classify(hsv)

            colors.add(color)
        }

        return colors
    }
}