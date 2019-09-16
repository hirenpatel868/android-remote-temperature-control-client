package com.smlnskgmail.jaman.remotetemperaturecontrol.monitor.connection

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import com.smlnskgmail.jaman.remotetemperaturecontrol.monitor.entities.signaltype.SignalTarget
import com.smlnskgmail.jaman.remotetemperaturecontrol.monitor.entities.signaltype.SignalType
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class MonitorBtConnection(

    btAdapter: BluetoothAdapter,
    deviceMacAddress: String,
    private val signalTarget: SignalTarget

) : Thread() {

    private val btUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private var btSocket: BluetoothSocket?

    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    private var isRunning = false

    init {
        val btDevice = btAdapter.getRemoteDevice(deviceMacAddress)
        btSocket = btDevice.createRfcommSocketToServiceRecord(btUuid)
    }

    override fun run() {
        runRead()
        while (canRead()) {
            read()
        }
    }

    private fun read() {
        if (inputStreamIsOpen()) {
            val rawData = inputStream!!.bufferedReader().readLine()
            val signalType = DataValidation.signalType(rawData)
            val data = DataValidation.data(rawData)
            signalTarget.onNewDataAvailable(signalType, data)
        }
    }

    private fun inputStreamIsOpen() = inputStream!!.bufferedReader().ready()

    fun send(signalType: SignalType) {
        outputStream!!.write(SignalType.signalOf(signalType).toByteArray())
    }

    fun connect() {
        if (!btSocket!!.isConnected) {
            btSocket!!.connect()
            inputStream = btSocket!!.inputStream
            outputStream = btSocket!!.outputStream
        }
    }

    fun disconnect() {
        if (btSocket!!.isConnected) {
            isRunning = false
            btSocket!!.close()
            inputStream!!.close()
            outputStream!!.close()

            btSocket = null
            inputStream = null
            outputStream = null
        }
    }

    fun handleOnResume() {
        if (outputStream != null) {
            outputStream!!.flush()
        }
    }

    private fun runRead() {
        isRunning = true
    }

    private fun canRead() = isRunning

}