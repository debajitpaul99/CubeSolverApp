package com.debajit.cubesolver.solver

import com.debajit.cubesolver.model.CubeColor

object CubeStringBuilder {

    fun build(
        faces: Map<CubeColor, List<CubeColor>>
    ): String {

        val builder = StringBuilder()

        appendFace(
            builder,
            faces[CubeColor.WHITE],
            'U'
        )

        appendFace(
            builder,
            faces[CubeColor.RED],
            'R'
        )

        appendFace(
            builder,
            faces[CubeColor.GREEN],
            'F'
        )

        appendFace(
            builder,
            faces[CubeColor.YELLOW],
            'D'
        )

        appendFace(
            builder,
            faces[CubeColor.ORANGE],
            'L'
        )

        appendFace(
            builder,
            faces[CubeColor.BLUE],
            'B'
        )

        return builder.toString()
    }

    private fun appendFace(
        builder: StringBuilder,
        stickers: List<CubeColor>?,
        faceLetter: Char
    ) {

        if (stickers == null) return

        stickers.forEach {

            builder.append(
                cubeColorToFaceLetter(it)
            )

        }

    }

    private fun cubeColorToFaceLetter(
        color: CubeColor
    ): Char {

        return when (color) {

            CubeColor.WHITE -> 'U'

            CubeColor.RED -> 'R'

            CubeColor.GREEN -> 'F'

            CubeColor.YELLOW -> 'D'

            CubeColor.ORANGE -> 'L'

            CubeColor.BLUE -> 'B'

            else -> 'X'

        }

    }

}