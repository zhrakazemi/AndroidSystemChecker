package com.example.androidsystemchecker


import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.Calendar


class Broadcast(val notification: NotificationCompat.Builder, val fService: Service) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d("NetworkCheckReceiver", "NetworkCheckReceiver invoked...")
            val noConnectivity = intent!!.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            )
            if (!noConnectivity) {
                Log.d("NetworkCheckReceiver", "connected")
                writeData("network connection" , Calendar.getInstance().time.toString() ,"connected" )
                notification.setContentText("network connect")
                fService.startForeground(1,notification.build())
            } else {
                Log.d("NetworkCheckReceiver", "disconnected")
                writeData("network connection" , Calendar.getInstance().time.toString() , "disconnected" )
                notification.setContentText("network not connect")
                fService.startForeground(1,notification.build())
            }
        }
    }
}