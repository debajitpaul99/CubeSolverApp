package com.debajit.cubesolver.userinterface.screens

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.debajit.cubesolver.R

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debajit.cubesolver.solver.NativeSolver
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomeScreen(
    onScanClick: () -> Unit,
    isBluetoothConnected: Boolean,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = "CubeSolver",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.rubiks_cube_logo),
            contentDescription = "Rubik's Cube",

            modifier = Modifier.height(160.dp),

            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Scan your Rubik's Cube"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onScanClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Scan Cube")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                val result = NativeSolver.solveCube("dummy")

                Toast.makeText(
                    context,
                    result,
                    Toast.LENGTH_LONG
                ).show()

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("JNI Test")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Robot Status",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isBluetoothConnected)
                        "🟢 Bluetooth Connected"
                    else
                        "🔴 Bluetooth Disconnected",
                    fontSize = 16.sp
                )

            }
        }
    }

}