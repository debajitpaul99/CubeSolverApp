package com.debajit.cubesolver.camera

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CubeOverlay(
    modifier: Modifier = Modifier
) {

    Canvas(modifier = modifier) {

        val gridSize = size.minDimension * 0.60f

        val left = (size.width - gridSize) / 2f
        val top = (size.height - gridSize) / 2f

        val cell = gridSize / 3f

        val samplingPoints = GridCalculator.calculateGrid(
            left = left,
            top = top,
            cell = cell
        )

        // Outer square
        drawRect(
            color = Color.White,
            topLeft = Offset(left, top),
            size = Size(gridSize, gridSize),
            style = Stroke(4.dp.toPx())
        )

        // Grid
        for (i in 1..2) {

            val x = left + i * cell
            val y = top + i * cell

            drawLine(
                color = Color.White,
                start = Offset(x, top),
                end = Offset(x, top + gridSize),
                strokeWidth = 3.dp.toPx()
            )

            drawLine(
                color = Color.White,
                start = Offset(left, y),
                end = Offset(left + gridSize, y),
                strokeWidth = 3.dp.toPx()
            )
        }

        // 9 Sampling Points
        samplingPoints.forEach { point ->

            drawCircle(
                color = Color.Green,
                radius = 5.dp.toPx(),
                center = point.position
            )

        }

        // Center Marker
        drawCircle(
            color = Color.Red,
            radius = 7.dp.toPx(),
            center = Offset(
                left + gridSize / 2,
                top + gridSize / 2
            )
        )

    }

}