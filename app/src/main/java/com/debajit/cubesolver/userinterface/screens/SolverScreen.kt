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
import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    var sending by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

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

    if (loading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CircularProgressIndicator()

                Spacer(modifier = Modifier.height(16.dp))

                Text("Solving Cube...")

            }

        }

    } else {

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

            Text(
                text = "Total Moves: ${moves.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

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

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                enabled = !sending && protocol.isNotBlank(),

                onClick = {

                    scope.launch {

                        sending = true

                        try {

                            withContext(Dispatchers.IO) {

                                val bluetooth = BluetoothManager()

                                val device = bluetooth.findCubeRobot()

                                if (device == null) {

                                    Log.e("CubeSolver", "CubeRobot not found")
                                    return@withContext

                                }

                                if (!bluetooth.connect(device)) {

                                    Log.e("CubeSolver", "Connection failed")
                                    return@withContext

                                }

                                bluetooth.send(protocol)

                                bluetooth.disconnect()

                            }

                        } catch (e: Exception) {

                            Log.e("CubeSolver", "Bluetooth Error", e)

                        } finally {

                            sending = false

                        }

                    }

                }

            ) {

                Text(
                    if (sending)
                        "Sending..."
                    else
                        "Start Robot"
                )

            }

        }

    }

}