package com.debajit.cubesolver.vision

import android.graphics.Color

object ColorConverter {

    fun rgbToHsv(
        rgb: RgbColor
    ): HsvColor {

        val hsv = FloatArray(3)

        Color.RGBToHSV(
            rgb.r,
            rgb.g,
            rgb.b,
            hsv
        )

        return HsvColor(

            hue = hsv[0],

            saturation = hsv[1],

            value = hsv[2]

        )

    }

}