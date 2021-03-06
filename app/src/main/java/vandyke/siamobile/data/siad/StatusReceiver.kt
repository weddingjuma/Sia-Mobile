/*
 * Copyright (c) 2017 Nicholas van Dyke
 *
 * This file is subject to the terms and conditions defined in 'LICENSE.md'
 */

package vandyke.siamobile.data.siad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import vandyke.siamobile.data.local.Prefs

class StatusReceiver(val siadService: SiadService) : BroadcastReceiver() {

    private var batteryGood: Boolean = true
    private var networkGood: Boolean = true

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            batteryGood = SiadService.isBatteryGood(intent)
        } else if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            networkGood = SiadService.isConnectionGood(context)
        }

        if (!batteryGood) {
            siadService.siadNotification("Stopped - battery is below ${Prefs.localNodeMinBattery}%")
            siadService.stopSiad()
        } else if (!networkGood) {
            siadService.siadNotification("Stopped - not connected to WiFi")
            siadService.stopSiad()
        } else {
            siadService.startSiad()
        }
        // TODO: it can be started in one instance when it shouldn't be, I don't remember when, so check later
    }
}
