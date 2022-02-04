package com.example.dictionaryapp.services

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.repository.DictionaryRepository
import com.example.dictionaryapp.ui.ListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmService(name: String = "AlarmService"): IntentService(name) {
    @Inject
    lateinit var repository: DictionaryRepository
    private var word: Def? = null

    override fun onHandleIntent(p0: Intent?) {
        val i = Intent(applicationContext, ListFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, i, 0)

        word = repository.getRandomRandomWordForNotification().value

        val notification = NotificationCompat.Builder(this.applicationContext, "channelID")
            .setSmallIcon(R.drawable.ic_add_notification)
            .setContentTitle("Daily Reminder")
            .setContentText(/*"${word!!.text} - ${word!!.tr[0].text}"*/"")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(123, notification)
    }
}