package com.debajit.cubesolver.vision

import androidx.compose.ui.geometry.Offset

object GridCalculator {

    fun calculateGrid(

        left: Float,

        top: Float,

        cell: Float

    ): List<SamplingPoint> {

        val points = mutableListOf<SamplingPoint>()

        for (row in 0..2) {

            for (column in 0..2) {

                points.add(

                    SamplingPoint(

                        row,

                        column,

                        Offset(

                            left + cell * (column + 0.5f),

                            top + cell * (row + 0.5f)

                        )

                    )

                )

            }

        }

        return points

    }

}