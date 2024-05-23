package com.example.androidsystemchecker


import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.work.BackoffPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.androidsystemchecker.ui.theme.AndroidSystemCheckerTheme
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.Duration
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
            super.onCreate(savedInstanceState)


            val fileName = "temp.json"
            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(folder, fileName)
            if (!file.exists()) {
                return
            }
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            var line = bufferedReader.readLine()
            val logs = mutableListOf<String>()
            while (line != null) {
                val jsonObject: JSONObject = JSONObject(line)
                logs.add(

                    jsonObject.get("type").toString() + "." +
                            jsonObject.get("time").toString() + "." +
                            jsonObject.get("value").toString()

                )
                line = bufferedReader.readLine()
            }
            setContent {

                AndroidSystemCheckerTheme {
                    LaunchedEffect(key1 = Unit) {
                        val workRequest =
                            PeriodicWorkRequestBuilder<CustomWorker>(
                                repeatInterval = 15,
                                repeatIntervalTimeUnit = TimeUnit.MINUTES,
                            ).setBackoffCriteria(
                                backoffPolicy = BackoffPolicy.LINEAR,
                                duration = Duration.ofSeconds(15)
                            ).build()

                        val workManager =
                            WorkManager.getInstance(applicationContext)
                        workManager.enqueue(workRequest)
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        LazyColumn {
                            items(logs){
                                val data = it.split(".")
                                Text(text = data[0] , modifier = Modifier.padding(2.dp) )
                                Text(text = data[1], modifier = Modifier.padding(2.dp) )
                                Text(text = data[2], modifier = Modifier.padding(2.dp) )

                            }
                        }
                        Row (modifier = Modifier.padding(top = 30.dp )){

                            Button(onClick = {
                                Intent(
                                    applicationContext,
                                    ForegroundService::class.java
                                ).also {
                                    it.action =
                                        ForegroundService.Actions.START.toString()
                                    startService(it)
                                }
                            }) {
                                Text(text = "Star Service")
                            }

                            Button(onClick = {
                                Intent(
                                    applicationContext,
                                    ForegroundService::class.java
                                ).also {
                                    it.action =
                                        ForegroundService.Actions.STOP.toString()
                                    startService(it)
                                }
                            }
                            ) {
                                Text(text = "Stop Service")
                            }
                        }
                    }
                }
            }
        }
    }
}