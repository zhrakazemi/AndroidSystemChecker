package com.example.androidsystemchecker

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import java.util.Calendar


class CustomWorker constructor(
    context: Context,
    workerParameters: WorkerParameters,
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val airPlaneModeOn = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON
        ) != 0
        val bluetoothon = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.BLUETOOTH_ON
        ) != 0

        Log.i("Broadcast Receiver", "airplane mode: $airPlaneModeOn")
        Log.i("Broadcast Receiver", "Bluetooth: $bluetoothon")
        writeData("airplane" , Calendar.getInstance().time.toString() , airPlaneModeOn.toString() )
        writeData("bluetooth" , Calendar.getInstance().time.toString() ,bluetoothon.toString() )
        return Result.success()
    }
}