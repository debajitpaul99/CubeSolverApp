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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                            CameraScreen()

                        }

                    }

                }

            }

        }

    }

}