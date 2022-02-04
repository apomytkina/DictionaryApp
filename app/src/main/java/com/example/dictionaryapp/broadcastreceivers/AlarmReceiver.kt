package com.example.dictionaryapp.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.dictionaryapp.services.AlarmService

class AlarmReceiver(): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = Intent(context, AlarmService::class.java)
        ContextCompat.startForegroundService(context, service)
    }
}