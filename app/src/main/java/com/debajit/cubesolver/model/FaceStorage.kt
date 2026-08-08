package com.debajit.cubesolver.model

object FaceStorage {

    private val images =
        mutableMapOf<CubeColor, String>()

    fun saveImage(
        face: CubeColor,
        imagePath: String
    ) {
        images[face] = imagePath
    }

    fun getImage(
        face: CubeColor
    ): String? {
        return images[face]
    }

    fun getAllImages(): Map<CubeColor, String> {
        return images
    }

    fun isComplete(): Boolean {
        return images.size == 6
    }

    fun clear() {
        images.clear()
    }

}