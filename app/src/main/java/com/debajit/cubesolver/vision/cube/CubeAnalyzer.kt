package com.debajit.cubesolver.vision.cube

import android.graphics.BitmapFactory
import com.debajit.cubesolver.model.CubeColor
import com.debajit.cubesolver.model.FaceStorage
import com.debajit.cubesolver.vision.bitmap.BitmapAnalyzer

object CubeAnalyzer {

    fun analyzeCube(): Map<CubeColor, List<CubeColor>> {

        val result = mutableMapOf<CubeColor, List<CubeColor>>()

        FaceStorage.getAllImages().forEach { (face, imagePath) ->

            val bitmap = BitmapFactory.decodeFile(imagePath)
                ?: return@forEach

            val scan = BitmapAnalyzer.analyze(bitmap)

            result[face] = scan.colors

        }

        return result

    }

}