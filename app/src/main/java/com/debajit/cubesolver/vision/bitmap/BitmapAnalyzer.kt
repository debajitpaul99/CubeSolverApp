package com.debajit.cubesolver.vision.bitmap

import android.graphics.Bitmap
import android.graphics.Color
import com.debajit.cubesolver.model.CubeColor
import com.debajit.cubesolver.vision.ColorClassifier
import com.debajit.cubesolver.vision.ColorConverter
import com.debajit.cubesolver.vision.HsvColor
import com.debajit.cubesolver.vision.RgbColor
import com.debajit.cubesolver.vision.sticker.StickerExtractor

/**
 * Result of analyzing a cube face bitmap.
 */
data class BitmapAnalysisResult(
    val colors: List<CubeColor>,
    val centerHsv: HsvColor
)

/**
 * Utility to analyze bitmaps of Rubik's cube faces.
 */
object BitmapAnalyzer {

    /**
     * Analyzes a bitmap to extract colors and the HSV value of the center sticker.
     */
    fun analyze(bitmap: Bitmap): BitmapAnalysisResult {
        val stickers = StickerExtractor.extract(bitmap)
        val colors = stickers.mapIndexed { index, sticker ->

            val hsv = getAverageHsv(sticker.bitmap)

            android.util.Log.d(
                "BitmapAnalyzer",
                "Sticker $index HSV = $hsv"
            )

            val cubeColor = ColorClassifier.classify(hsv)

            android.util.Log.d(
                "BitmapAnalyzer",
                "Sticker $index -> $cubeColor"
            )

            cubeColor
        }

        // The 5th sticker (index 4) is the center one in a 3x3 grid
        val centerSticker = stickers[4]
        val centerHsv = getAverageHsv(centerSticker.bitmap)

        return BitmapAnalysisResult(colors, centerHsv)
    }

    private fun getAverageHsv(bitmap: Bitmap): HsvColor {

        var r = 0L
        var g = 0L
        var b = 0L
        var count = 0L

        val cx = bitmap.width / 2
        val cy = bitmap.height / 2

        val radius = minOf(bitmap.width, bitmap.height) / 3

        for (y in 0 until bitmap.height) {

            for (x in 0 until bitmap.width) {

                val dx = x - cx
                val dy = y - cy

                if (dx * dx + dy * dy <= radius * radius) {

                    val pixel = bitmap.getPixel(x, y)

                    r += Color.red(pixel).toLong()
                    g += Color.green(pixel).toLong()
                    b += Color.blue(pixel).toLong()

                    count++

                }

            }

        }

        if (count == 0L) {
            return HsvColor(
                hue = 0f,
                saturation = 0f,
                value = 0f
            )
        }

        val avgR = (r / count).toInt()
        val avgG = (g / count).toInt()
        val avgB = (b / count).toInt()

        return ColorConverter.rgbToHsv(
            RgbColor(avgR, avgG, avgB)
        )

    }
}
