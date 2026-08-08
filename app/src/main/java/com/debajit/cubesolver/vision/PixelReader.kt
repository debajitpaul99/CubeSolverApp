package com.debajit.cubesolver.vision

import androidx.camera.core.ImageProxy

object PixelReader {

    fun getY(
        image: ImageProxy,
        x: Int,
        y: Int
    ): Int {

        val plane = image.planes[0]

        val buffer = plane.buffer

        val rowStride = plane.rowStride

        val bytes = ByteArray(buffer.remaining())

        buffer.get(bytes)

        return bytes[
            y * rowStride + x
        ].toInt() and 0xFF
    }

    fun getAverageY(
        image: ImageProxy,
        centerX: Int,
        centerY: Int,
        radius: Int
    ): Int {

        val plane = image.planes[0]

        val buffer = plane.buffer

        val rowStride = plane.rowStride

        val bytes = ByteArray(buffer.remaining())

        buffer.get(bytes)

        var sum = 0

        var count = 0

        for (y in centerY - radius..centerY + radius) {

            for (x in centerX - radius..centerX + radius) {

                if (x < 0 || y < 0) continue

                if (x >= image.width) continue

                if (y >= image.height) continue

                val value = bytes[
                    y * rowStride + x
                ].toInt() and 0xFF

                sum += value

                count++

            }

        }

        return sum / count

    }
}