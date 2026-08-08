package com.debajit.cubesolver.vision.opencv

import android.graphics.Bitmap
import android.util.Log
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

object OpenCvProcessor {

    fun enhance(bitmap: Bitmap): Bitmap {

        return try {

            val src = Mat()

            Utils.bitmapToMat(bitmap, src)

            val result = Mat()

            Imgproc.GaussianBlur(
                src,
                result,
                Size(5.0, 5.0),
                0.0
            )

            val output = Bitmap.createBitmap(
                bitmap.width,
                bitmap.height,
                Bitmap.Config.ARGB_8888
            )

            Utils.matToBitmap(result, output)

            src.release()
            result.release()

            output

        } catch (e: Exception) {

            Log.e(
                "OpenCV",
                "Enhance failed",
                e
            )

            bitmap
        }
    }
}