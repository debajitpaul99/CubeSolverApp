package com.debajit.cubesolver.camera

import androidx.compose.ui.geometry.Offset

/**
 * Data class representing a point in the sampling grid.
 */
data class GridPoint(
    val position: Offset
)

/**
 * Utility to calculate sampling points for a 3x3 Rubik's cube grid.
 */
object GridCalculator {
    /**
     * Calculates 9 points corresponding to the centers of a 3x3 grid.
     *
     * @param left The left boundary of the grid.
     * @param top The top boundary of the grid.
     * @param cell The width/height of a single cell in the grid.
     * @return A list of 9 [GridPoint]s.
     */
    fun calculateGrid(
        left: Float,
        top: Float,
        cell: Float
    ): List<GridPoint> {
        val points = mutableListOf<GridPoint>()
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                // Calculate the center of each sticker cell
                val x = left + col * cell + cell / 2f
                val y = top + row * cell + cell / 2f
                points.add(GridPoint(Offset(x, y)))
            }
        }
        return points
    }
}
