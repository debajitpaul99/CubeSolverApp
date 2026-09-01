package com.debajit.cubesolver.model

object CubeScanSession {

    private val faces = mutableMapOf<CubeColor, MutableList<CubeColor>>()

    // Scan order (optimized for user)
    private val faceOrder = listOf(
        CubeColor.WHITE,
        CubeColor.BLUE,
        CubeColor.ORANGE,
        CubeColor.GREEN,
        CubeColor.RED,
        CubeColor.YELLOW
    )

    private var currentIndex = 0

    val currentFace: CubeColor
        get() = faceOrder[currentIndex]

    val currentStep: Int
        get() = currentIndex + 1

    val isLastFace: Boolean
        get() = currentIndex == faceOrder.lastIndex

    var editingColors = MutableList(9) {
        CubeColor.UNKNOWN
    }

    var capturedImagePath: String? = null

    fun saveCurrentFace() {

        faces[currentFace] = editingColors.toMutableList()

    }

    fun getFace(
        face: CubeColor
    ): List<CubeColor>? {

        return faces[face]

    }

    fun scannedFaceCount(): Int {

        return faces.size

    }

    fun allFacesScanned(): Boolean {

        return faces.size == 6

    }

    fun hasNextFace(): Boolean {

        return currentIndex < faceOrder.lastIndex

    }

    fun isFinished(): Boolean {

        return scannedFaceCount() == 6

    }

    fun nextFace() {

        if (hasNextFace()) {

            currentIndex++

        }

    }

    /**
     * Instruction shown on the camera screen.
     */
    fun scanInstruction(): String {

        return when (currentFace) {

            CubeColor.WHITE ->
                "Hold the cube with the WHITE face toward the camera. Do NOT rotate the face."

            CubeColor.BLUE ->
                "Keep the WHITE face on TOP. Rotate the WHOLE cube RIGHT. Scan the BLUE face."

            CubeColor.ORANGE ->
                "Keep the WHITE face on TOP. Rotate the WHOLE cube RIGHT again. Scan the ORANGE face."

            CubeColor.GREEN ->
                "Keep the WHITE face on TOP. Rotate the WHOLE cube RIGHT again. Scan the GREEN face."

            CubeColor.RED ->
                "Keep the WHITE face on TOP. Rotate the WHOLE cube RIGHT again. Scan the RED face."

            CubeColor.YELLOW ->
                "Turn the WHOLE cube UPSIDE DOWN. Scan the YELLOW face."

            else -> ""
        }

    }

    fun reset() {

        currentIndex = 0

        faces.clear()

        editingColors = MutableList(9) {
            CubeColor.UNKNOWN
        }

        capturedImagePath = null

    }

}