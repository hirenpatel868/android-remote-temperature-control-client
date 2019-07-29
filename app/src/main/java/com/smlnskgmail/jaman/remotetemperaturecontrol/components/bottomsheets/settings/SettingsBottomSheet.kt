package com.smlnskgmail.jaman.remotetemperaturecontrol.components.bottomsheets.settings

import com.smlnskgmail.jaman.remotetemperaturecontrol.R
import com.smlnskgmail.jaman.remotetemperaturecontrol.components.bottomsheets.BaseBottomSheet
import kotlinx.android.synthetic.main.bottom_sheet_settings.*

class SettingsBottomSheet : BaseBottomSheet() {

    private var disconnectListener: OnDisconnectListener? = null

    override fun initialize() {
        settings_disconnect.setOnClickListener {
            disconnectListener?.onConnectionClosed()
        }
    }

    fun setBluetoothDisconnectListener(onDisconnectListener: OnDisconnectListener) {
        this.disconnectListener = onDisconnectListener
    }

    override fun getLayoutResId() = R.layout.bottom_sheet_settings

}