package com.debajit.cubesolver.vision.bitmap

import android.graphics.Bitmap
import android.graphics.Color
import com.debajit.cubesolver.model.CubeColor
import com.debajit.cubesolver.vision.ColorClassifier
import com.debajit.cubesolver.vision.ColorConverter
import com.debajit.cubesolver.vision.HsvColor
import com.debajit.cubesolver.vision.RgbColor
import com.debajit.cubesolver.vision.opencv.OpenCvProcessor

object BitmapAnalyzer {

    fun analyze(
        bitmap: Bitmap
    ): BitmapScanResult {

        val enhancedBitmap = bitmap

        val width = enhancedBitmap.width
        val height = enhancedBitmap.height

        val gridSize = minOf(width, height) * 0.60f

        val left = (width - gridSize) / 2f
        val top = (height - gridSize) / 2f

        val cell = gridSize / 3f

        val colors = mutableListOf<CubeColor>()

        var centerHsv = HsvColor(
            hue = 0f,
            saturation = 0f,
            value = 0f
        )

        for (row in 0..2) {

            for (col in 0..2) {

                val centerX =
                    (left + cell * (col + 0.5f)).toInt()

                val centerY =
                    (top + cell * (row + 0.5f)).toInt()

                val sampleRadius = (cell * 0.12f).toInt().coerceAtLeast(6)

                val rgb = averageRgb(
                    enhancedBitmap,
                    centerX,
                    centerY,
                    sampleRadius
                )

                val hsv = ColorConverter.rgbToHsv(rgb)

                if (row == 1 && col == 1) {
                    centerHsv = hsv
                }

                val cubeColor =
                    ColorClassifier.classify(hsv)

                colors.add(cubeColor)
            }
        }

        return BitmapScanResult(
            colors = colors,
            centerHsv = centerHsv
        )
    }

    private fun averageRgb(
        bitmap: Bitmap,
        centerX: Int,
        centerY: Int,
        radius: Int
    ): RgbColor {

        var r = 0
        var g = 0
        var b = 0
        var count = 0

        val radiusSquared = radius * radius

        for (y in centerY - radius..centerY + radius) {

            if (y !in 0 until bitmap.height) continue

            for (x in centerX - radius..centerX + radius) {

                if (x !in 0 until bitmap.width) continue

                val dx = x - centerX
                val dy = y - centerY

                // Only sample pixels inside the circle
                if (dx * dx + dy * dy > radiusSquared) {
                    continue
                }

                val pixel = bitmap.getPixel(x, y)

                r += Color.red(pixel)
                g += Color.green(pixel)
                b += Color.blue(pixel)

                count++
            }
        }

        if (count == 0) {
            return RgbColor(0, 0, 0)
        }

        return RgbColor(
            r / count,
            g / count,
            b / count
        )
    }
}