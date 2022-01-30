package com.example.dictionaryapp.util

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dictionaryapp.R
import com.example.dictionaryapp.ui.ListFragment

class AlarmReceiver(): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, ListFragment::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // ?
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val notification = NotificationCompat.Builder(context!!, "channelID")
            .setSmallIcon(R.drawable.ic_add_notification)
            .setContentTitle("Daily Reminder")
            .setContentText("Word - Translation")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, notification)
    }
}