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
import com.debajit.cubesolver.userinterface.screens.EditableFaceScreen
import com.debajit.cubesolver.userinterface.screens.SolverScreen
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private val bluetoothPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            // We can handle permission results later if needed.

        }
    //Bluetooth permission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            val permissions = arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )

            val missingPermission = permissions.any {

                ContextCompat.checkSelfPermission(
                    this,
                    it
                ) != PackageManager.PERMISSION_GRANTED

            }

            if (missingPermission) {

                bluetoothPermissionLauncher.launch(
                    permissions
                )

            }

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