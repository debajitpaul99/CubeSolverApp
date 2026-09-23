package com.debajit.cubesolver.userinterface.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.debajit.cubesolver.model.CubeColor

@Composable
fun EditableColorGrid(

    colors: List<CubeColor>,

    onStickerClick: (Int) -> Unit

) {

    Column(

        verticalArrangement = Arrangement.spacedBy(2.dp)

    ) {

        for (row in 0 until 3) {

            Row(

                horizontalArrangement = Arrangement.spacedBy(2.dp)

            ) {

                for (col in 0 until 3) {

                    val index = row * 3 + col

                    Box(

                        modifier = Modifier

                            .size(80.dp)

                            .background(

                                when (colors[index]) {

                                    CubeColor.WHITE -> Color.White

                                    CubeColor.YELLOW -> Color.Yellow

                                    CubeColor.RED -> Color.Red

                                    CubeColor.ORANGE -> Color(0xFFFF9800)

                                    CubeColor.GREEN -> Color.Green

                                    CubeColor.BLUE -> Color.Blue

                                    CubeColor.UNKNOWN -> Color.DarkGray

                                }

                            )

                            .border(2.dp, Color.Black)

                            .clickable {

                                onStickerClick(index)

                            }

                    )

                }

            }

        }

    }

}