package com.debajit.cubesolver.vision

import com.debajit.cubesolver.model.CubeColor

object ColorClassifier {

    fun classify(hsv: HsvColor): CubeColor {

        if (!CalibrationManager.isComplete()) {
            return CubeColor.UNKNOWN
        }

        var bestColor = CubeColor.UNKNOWN
        var bestDistance = Float.MAX_VALUE

        CalibrationManager.allReferences().forEach { (color, reference) ->

            val distance = ColorDistance.hsvDistance(
                hsv,
                reference
            )

            if (distance < bestDistance) {
                bestDistance = distance
                bestColor = color
            }
        }

        return bestColor
    }
}