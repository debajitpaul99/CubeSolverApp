package com.debajit.cubesolver.vision

import com.debajit.cubesolver.model.CubeColor
import com.debajit.cubesolver.model.CubeScanSession

object CubeStringBuilder {

    fun build(): String {

        val order = listOf(
            CubeColor.WHITE,    // U
            CubeColor.BLUE,     // R
            CubeColor.RED,      // F
            CubeColor.YELLOW,   // D
            CubeColor.GREEN,    // L
            CubeColor.ORANGE    // B
        )

        val builder = StringBuilder()

        order.forEach { face ->

            var stickers = CubeScanSession.getFace(face)
                ?: return ""

            stickers.forEach {

                builder.append(
                    cubeColorToFaceLetter(it)
                )

            }

        }

        return builder.toString()

    }

    private fun cubeColorToFaceLetter(
        color: CubeColor
    ): Char {

        return when (color) {

            CubeColor.WHITE  -> 'U'

            CubeColor.BLUE   -> 'R'

            CubeColor.RED    -> 'F'

            CubeColor.YELLOW -> 'D'

            CubeColor.GREEN  -> 'L'

            CubeColor.ORANGE -> 'B'

            else -> 'X'
        }

    }

}