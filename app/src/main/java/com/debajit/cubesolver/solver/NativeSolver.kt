package com.debajit.cubesolver.solver

object NativeSolver {

    init {
        System.loadLibrary("cubesolver")
    }

    external fun solveCube(
        cubeState: String
    ): String
}