package com.debajit.cubesolver.solver

import com.debajit.cubesolver.model.CubeColor

object CubeValidator {

    fun validate(
        faces: Map<CubeColor, List<CubeColor>>
    ): Boolean {

        val counts = mutableMapOf<CubeColor, Int>()

        CubeColor.values().forEach {
            counts[it] = 0
        }

        faces.values.forEach { stickers ->

            stickers.forEach { color ->

                counts[color] =
                    (counts[color] ?: 0) + 1

            }

        }

        return listOf(
            CubeColor.WHITE,
            CubeColor.YELLOW,
            CubeColor.RED,
            CubeColor.ORANGE,
            CubeColor.GREEN,
            CubeColor.BLUE
        ).all {

            counts[it] == 9

        }

    }
}