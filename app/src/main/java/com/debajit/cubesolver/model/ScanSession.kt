package com.debajit.cubesolver.model

object ScanSession {

    private val order = listOf(
        CubeColor.WHITE,
        CubeColor.RED,
        CubeColor.GREEN,
        CubeColor.YELLOW,
        CubeColor.ORANGE,
        CubeColor.BLUE
    )

    private var index = 0

    fun currentFace(): CubeColor {

        return if (index >= order.size)
            CubeColor.UNKNOWN
        else
            order[index]

    }

    fun nextFace() {

        if (index < order.size)
            index++

    }

    fun isFinished(): Boolean {

        return index >= order.size

    }

    fun reset() {

        index = 0

    }

    fun currentStep(): Int {

        return (index + 1).coerceAtMost(6)

    }

}