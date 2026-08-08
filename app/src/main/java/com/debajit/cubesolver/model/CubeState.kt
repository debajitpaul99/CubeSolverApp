package com.debajit.cubesolver.model

object CubeState {

    private val faces = mutableMapOf<CubeColor, List<CubeColor>>()

    fun saveFace(
        face: CubeColor,
        colors: List<CubeColor>
    ) {
        faces[face] = colors
    }

    fun getFace(
        face: CubeColor
    ): List<CubeColor>? {
        return faces[face]
    }

    fun isComplete(): Boolean {
        return faces.size == 6
    }

    fun clear() {
        faces.clear()
    }

    fun allFaces(): Map<CubeColor, List<CubeColor>> {
        return faces
    }
}