package com.smlnskgmail.jaman.remotetemperaturecontrol.monitor.utils

import com.smlnskgmail.jaman.remotetemperaturecontrol.entities.signal.SignalType

object DataParser {

    fun getSignalType(rawData: String): SignalType {
        if (rawData.isEmpty()) {
            return SignalType.Nothing
        }
        val controlSymbol = rawData[0].toString()
        return when (controlSymbol) {
            SignalType.Temperature.signal -> SignalType.Temperature
            SignalType.TemperatureMaximum.signal -> SignalType.TemperatureMaximum
            SignalType.TemperatureMinimum.signal -> SignalType.TemperatureMinimum
            SignalType.Humidity.signal -> SignalType.Humidity
            SignalType.HumidityMaximum.signal -> SignalType.HumidityMaximum
            SignalType.HumidityMinimum.signal -> SignalType.HumidityMinimum
            else -> SignalType.Reset
        }
    }

    fun getData(rawData: String): String {
        if (rawData.isEmpty()) {
            return "";
        }
        return rawData.substring(1)
    }

}