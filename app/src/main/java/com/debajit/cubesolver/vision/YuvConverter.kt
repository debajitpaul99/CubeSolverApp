package com.debajit.cubesolver.vision

import android.graphics.ImageFormat
import androidx.camera.core.ImageProxy

object YuvConverter {

    fun getAverageRgb(
        image: ImageProxy,
        centerX: Int,
        centerY: Int,
        radius: Int
    ): RgbColor {

        require(image.format == ImageFormat.YUV_420_888)

        val yPlane = image.planes[0]
        val uPlane = image.planes[1]
        val vPlane = image.planes[2]

        val yBuffer = yPlane.buffer
        val uBuffer = uPlane.buffer
        val vBuffer = vPlane.buffer

        val yRowStride = yPlane.rowStride
        val uRowStride = uPlane.rowStride
        val vRowStride = vPlane.rowStride

        val yPixelStride = yPlane.pixelStride
        val uPixelStride = uPlane.pixelStride
        val vPixelStride = vPlane.pixelStride

        var sumR = 0
        var sumG = 0
        var sumB = 0
        var count = 0

        for (y in centerY - radius..centerY + radius) {

            if (y < 0 || y >= image.height) continue

            for (x in centerX - radius..centerX + radius) {

                if (x < 0 || x >= image.width) continue

                val yIndex =
                    y * yRowStride + x * yPixelStride

                val uvX = x / 2
                val uvY = y / 2

                val uIndex =
                    uvY * uRowStride + uvX * uPixelStride

                val vIndex =
                    uvY * vRowStride + uvX * vPixelStride

                val Y = yBuffer.get(yIndex).toInt() and 0xFF
                val U = (uBuffer.get(uIndex).toInt() and 0xFF) - 128
                val V = (vBuffer.get(vIndex).toInt() and 0xFF) - 128

                var r = (Y + 1.402f * V).toInt()
                var g = (Y - 0.344136f * U - 0.714136f * V).toInt()
                var b = (Y + 1.772f * U).toInt()

                r = r.coerceIn(0, 255)
                g = g.coerceIn(0, 255)
                b = b.coerceIn(0, 255)

                sumR += r
                sumG += g
                sumB += b

                count++
            }
        }

        return RgbColor(
            r = sumR / count,
            g = sumG / count,
            b = sumB / count
        )
    }
}