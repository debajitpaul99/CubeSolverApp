package com.debajit.cubesolver.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class BluetoothManager {

    private var socket: BluetoothSocket? = null

    private var input: InputStream? = null

    private var output: OutputStream? = null

    companion object {

        val UUID_SPP: UUID =
            UUID.fromString(
                "00001101-0000-1000-8000-00805F9B34FB"
            )

    }

    fun findCubeRobot(): BluetoothDevice? {

        val adapter = BluetoothAdapter.getDefaultAdapter()
            ?: return null

        val pairedDevices = adapter.bondedDevices

        for (device in pairedDevices) {

            if (device.name == "CubeRobot") {

                return device

            }

        }

        return null

    }

    fun connect(
        device: BluetoothDevice
    ): Boolean {

        return try {

            disconnect()

            socket =
                device.createRfcommSocketToServiceRecord(
                    UUID_SPP
                )

            socket?.connect()

            input = socket?.inputStream

            output = socket?.outputStream

            true

        } catch (e: Exception) {

            e.printStackTrace()

            false

        }

    }

    fun send(
        text: String
    ): Boolean {

        return try {

            output?.write(
                text.toByteArray()
            )

            output?.flush()

            true

        } catch (e: Exception) {

            e.printStackTrace()

            false

        }

    }

    fun disconnect() {

        try {

            input?.close()

            output?.close()

            socket?.close()

        } catch (e: Exception) {

            e.printStackTrace()

        }

        input = null
        output = null
        socket = null

    }

}