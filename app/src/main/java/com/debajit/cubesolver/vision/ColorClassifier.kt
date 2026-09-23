package com.debajit.cubesolver.vision

import com.debajit.cubesolver.model.CubeColor

object ColorClassifier {

    fun classify(hsv: HsvColor): CubeColor {

        val h = hsv.hue
        val s = hsv.saturation
        val v = hsv.value

        // White
        if (s < 0.22f && v > 0.45f)
            return CubeColor.WHITE

        // Red
        if ((h < 15f || h >= 345f) && s > 0.45f)
            return CubeColor.RED

        // Orange
        if (h in 15f..40f && s > 0.45f)
            return CubeColor.ORANGE

        // Yellow
        if (h in 42f..78f)
            return CubeColor.YELLOW

        // Green
        if (h in 78f..170f)
            return CubeColor.GREEN

        // Blue
        if (h in 170f..265f)
            return CubeColor.BLUE

        return CubeColor.UNKNOWN
    }

}