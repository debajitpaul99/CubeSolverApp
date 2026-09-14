package com.debajit.cubesolver.userinterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.debajit.cubesolver.network.RetrofitClient
import com.debajit.cubesolver.network.SolveRequest
import com.debajit.cubesolver.vision.CubeStringBuilder
import com.debajit.cubesolver.bluetooth.BluetoothManager

@Composable
fun SolverScreen() {

    var solution by remember {
        mutableStateOf("")
    }

    var protocol by remember {
        mutableStateOf("")
    }

    var loading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {

        try {

            val cube = CubeStringBuilder.build()

            if (cube.length != 54) {
                solution = "Cube scan incomplete."
                loading = false
                return@LaunchedEffect
            }

            val response =
                RetrofitClient.api.solve(
                    SolveRequest(cube)
                )

            if (response.isSuccessful) {

                val body = response.body()

                if (body != null)
                {
                    solution = body.solution

                    protocol = body.protocol

                    val bluetooth = BluetoothManager()

                    val device = bluetooth.findCubeRobot()

                    if (device != null)
                    {
                        if (bluetooth.connect(device))
                        {
                            bluetooth.send(protocol)

                            bluetooth.disconnect()

                            android.util.Log.d(
                                "CubeSolver",
                                "Protocol sent to ESP32"
                            )
                        }
                        else
                        {
                            android.util.Log.e(
                                "CubeSolver",
                                "Failed to connect to CubeRobot"
                            )
                        }
                    }
                    else
                    {
                        android.util.Log.e(
                            "CubeSolver",
                            "CubeRobot not found"
                        )
                    }
                }
                else
                {
                    solution = "No solution returned"
                }

            } else {

                solution =
                    "Server Error: ${response.code()}"

            }

        } catch (e: Exception) {

            solution =
                "Network Error\n${e.message}"

        }

        loading = false

    }

    val moves = solution
        .trim()
        .split(" ")
        .filter {
            it.isNotBlank()
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Cube Solution",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (loading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CircularProgressIndicator()

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text("Solving Cube...")

                }

            }

        } else {

            Text(
                text = "Total Moves: ${moves.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {

                itemsIndexed(moves) { index, move ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "${index + 1}.",
                                modifier = Modifier.width(40.dp)
                            )

                            Text(
                                text = move,
                                style = MaterialTheme.typography.titleLarge
                            )

                        }

                    }

                }

            }

        }

    }

}