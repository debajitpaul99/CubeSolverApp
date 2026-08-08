package com.debajit.cubesolver.vision

import com.debajit.cubesolver.model.CubeColor

object CalibrationManager {

    private val references = mutableMapOf<CubeColor, HsvColor>()

    fun saveReference(
        color: CubeColor,
        hsv: HsvColor
    ) {
        references[color] = hsv
    }

    fun getReference(
        color: CubeColor
    ): HsvColor? {
        return references[color]
    }

    fun allReferences(): Map<CubeColor, HsvColor> {
        return references
    }

    fun isComplete(): Boolean {
        return references.size == 6
    }

    fun clear() {
        references.clear()
    }

    fun calibratedFaces(): Int {
        return references.size
    }
}