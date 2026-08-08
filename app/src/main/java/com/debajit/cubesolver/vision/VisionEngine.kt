package com.debajit.cubesolver.vision

object VisionEngine {

    fun createFrameData(

        width: Int,

        height: Int

    ): FrameData {

        val gridSize = minOf(width, height) * 0.60f

        val left = (width - gridSize) / 2f

        val top = (height - gridSize) / 2f

        val cell = gridSize / 3f

        val points = GridCalculator.calculateGrid(
            left,
            top,
            cell
        )

        return FrameData(

            width = width,

            height = height,

            samplingPoints = points.map {
                it.position
            }

        )

    }

}