package com.debajit.cubesolver.vision

import com.debajit.cubesolver.model.CubeColor

object ColorClassifier {

    fun classify(hsv: HsvColor): CubeColor {

        val h = hsv.hue
        val s = hsv.saturation
        val v = hsv.value

        // White
        if (s < 0.25f && v > 0.35f)
            return CubeColor.WHITE

        // Red
        if ((h >= 0f && h < 18f) || (h >= 335f && h <= 360f) || (h >= 18f && h < 23f && s > 0.75f))
            return CubeColor.RED

        // Orange
        if (h in 18f..42f)
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