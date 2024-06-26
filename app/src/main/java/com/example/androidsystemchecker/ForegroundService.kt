package com.example.androidsystemchecker

import android.app.BroadcastOptions
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start();
            Actions.STOP.toString() -> stop();
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "service_channel")
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Service is Running")
            .setContentText("Here is the notification content")
        registerReceiver(

            Broadcast(notification, this),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        )
        startForeground(1, notification.build())
    }

    private fun stop() {
        stopSelf()
    }

    enum class Actions {
        START, STOP
    }
}