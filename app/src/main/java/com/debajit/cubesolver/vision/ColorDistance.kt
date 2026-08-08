package com.debajit.cubesolver.vision

import kotlin.math.abs

object ColorDistance {

    fun hsvDistance(
        a: HsvColor,
        b: HsvColor
    ): Float {

        var hueDiff = abs(a.hue - b.hue)

        if (hueDiff > 180f)
            hueDiff = 360f - hueDiff

        val normalizedHue = hueDiff / 180f

        val saturationDiff =
            abs(a.saturation - b.saturation)

        val valueDiff =
            abs(a.value - b.value)

        return normalizedHue * 0.70f +
                saturationDiff * 0.20f +
                valueDiff * 0.10f

    }

}