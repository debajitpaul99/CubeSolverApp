package com.debajit.cubesolver.userinterface.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debajit.cubesolver.R
import androidx.compose.foundation.shape.RoundedCornerShape
import android.bluetooth.BluetoothAdapter
import androidx.compose.runtime.remember
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background

@Composable
fun HomeScreen(
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val bluetoothAdapter = remember {
        BluetoothAdapter.getDefaultAdapter()
    }

    val context = LocalContext.current

    val isRobotPaired = remember {

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            bluetoothAdapter?.bondedDevices?.any {
                it.name == "CubeRobot"
            } ?: false

        } else {

            false

        }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "IQube",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.rubiks_cube_logo),
            contentDescription = "Rubik's Cube",
            modifier = Modifier.height(220.dp),
            contentScale = ContentScale.Fit
        )


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onScanClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50.dp)
        )
        {
            Text(
                text = "Start Scan",
                fontSize = 18.sp
            )
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
                    text = "Status",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isRobotPaired)
                        "🟢 Bluetooth Paired"
                    else
                        "🔴 Bluetooth Not Paired",
                    fontSize = 16.sp
                )
            }
        }
    }
}