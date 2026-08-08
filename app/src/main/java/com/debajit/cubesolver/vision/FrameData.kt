package com.debajit.cubesolver.vision

import androidx.compose.ui.geometry.Offset

data class FrameData(

    val width: Int,

    val height: Int,

    val samplingPoints: List<Offset>

)