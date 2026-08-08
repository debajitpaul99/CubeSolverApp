package com.debajit.cubesolver.vision.bitmap

import com.debajit.cubesolver.model.CubeColor
import com.debajit.cubesolver.vision.HsvColor

data class BitmapScanResult(

    val colors: List<CubeColor>,

    val centerHsv: HsvColor

)