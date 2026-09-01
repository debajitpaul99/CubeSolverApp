package com.debajit.cubesolver.userinterface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.debajit.cubesolver.model.CubeColor
import com.debajit.cubesolver.model.CubeScanSession
import com.debajit.cubesolver.userinterface.components.EditableColorGrid

@Composable
fun EditableFaceScreen(
    onNextFace: () -> Unit,
    onSolve: () -> Unit,
    onRetake: () -> Unit
) {

    var colors by remember {
        mutableStateOf(
            CubeScanSession.editingColors.toMutableList()
        )
    }

    var selectedSticker by remember {
        mutableStateOf<Int?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Review ${CubeScanSession.currentFace} Face",
            color = Color.White
        )

        Text(
            text = "Face ${CubeScanSession.currentStep} of 6",
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        EditableColorGrid(

            colors = colors,

            onStickerClick = {

                selectedSticker = it

            }

        )

        Row(

            modifier = Modifier.padding(top = 32.dp),

            horizontalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            Button(

                onClick = {

                    onRetake()

                }

            ) {

                Text("Retake")

            }

            Button(

                onClick = {

                    CubeScanSession.editingColors = colors

                    CubeScanSession.saveCurrentFace()

                    if (CubeScanSession.allFacesScanned()) {

                        onSolve()

                    } else {

                        CubeScanSession.nextFace()

                        onNextFace()

                    }

                }

            ) {

                Text("Save Face")

            }

        }

    }

    if (selectedSticker != null) {

        AlertDialog(

            onDismissRequest = {

                selectedSticker = null

            },

            title = {

                Text("Choose Color")

            },

            text = {

                Column {

                    CubeColor.entries
                        .filter { it != CubeColor.UNKNOWN }
                        .forEach { color ->

                            Text(

                                text = color.name,

                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {

                                        colors = colors.toMutableList().also {

                                            it[selectedSticker!!] = color

                                        }

                                        selectedSticker = null

                                    }

                            )

                        }

                }

            },

            confirmButton = {

                TextButton(

                    onClick = {

                        selectedSticker = null

                    }

                ) {

                    Text("Cancel")

                }

            }

        )

    }

}