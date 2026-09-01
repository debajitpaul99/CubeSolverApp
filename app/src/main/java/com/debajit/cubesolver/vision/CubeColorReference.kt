package com.debajit.cubesolver.vision

import com.debajit.cubesolver.model.CubeColor

object CubeColorReference {

    private val references =
        mutableMapOf<CubeColor, HsvColor>()

    fun save(
        color: CubeColor,
        hsv: HsvColor
    ) {
        references[color] = hsv
    }

    fun get(
        color: CubeColor
    ) = references[color]

    fun all() = references

    fun clear() {
        references.clear()
    }
}