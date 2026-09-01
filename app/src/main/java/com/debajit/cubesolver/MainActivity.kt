package com.debajit.cubesolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.debajit.cubesolver.ui.theme.CubeSolverTheme
import com.debajit.cubesolver.userinterface.screens.CameraScreen
import com.debajit.cubesolver.userinterface.screens.HomeScreen
import org.opencv.android.OpenCVLoader
import com.debajit.cubesolver.userinterface.screens.EditableFaceScreen
import com.debajit.cubesolver.userinterface.screens.SolverScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (OpenCVLoader.initLocal()) {
            android.util.Log.d("OpenCV", "OpenCV initialized")
        } else {
            android.util.Log.e("OpenCV", "OpenCV initialization failed")
        }

        enableEdgeToEdge()

        setContent {

            CubeSolverTheme {

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("home") {

                            HomeScreen(
                                onScanClick = {
                                    navController.navigate("camera")
                                },
                                isBluetoothConnected = false
                            )

                        }

                        composable("camera") {

                            CameraScreen(

                                onScanFinished = {

                                    navController.navigate("editFace")

                                }

                            )

                        }

                        composable("editFace") {

                            EditableFaceScreen(

                                onNextFace = {

                                    navController.popBackStack()

                                },

                                onSolve = {

                                    navController.navigate("solver") {

                                        popUpTo("camera") {
                                            inclusive = true
                                        }

                                    }

                                },

                                onRetake = {

                                    navController.popBackStack()

                                }

                            )

                        }

                        composable("solver"){

                            SolverScreen()

                        }

                    }

                }

            }

        }

    }

}