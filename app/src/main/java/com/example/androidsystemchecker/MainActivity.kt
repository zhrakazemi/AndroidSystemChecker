        package com.example.androidsystemchecker


        import android.Manifest
        import android.content.Intent
        import android.os.Build
        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
       import androidx.annotation.RequiresApi
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.material3.MaterialTheme
        import androidx.compose.material3.Surface
        import androidx.compose.material3.Text
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.tooling.preview.Preview
        import androidx.work.BackoffPolicy
        import androidx.work.PeriodicWorkRequestBuilder
        import androidx.work.WorkManager
        import com.example.androidsystemchecker.ui.theme.AndroidSystemCheckerTheme
        import java.time.Duration
        import java.util.concurrent.TimeUnit
        import androidx.compose.foundation.layout.Arrangement
        import androidx.compose.foundation.layout.Column
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.material3.Button
        import androidx.compose.material3.ExperimentalMaterial3Api
        import androidx.compose.material3.Text
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.core.app.ActivityCompat
        import com.example.androidsystemchecker.ui.theme.AndroidSystemCheckerTheme



        class MainActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        0
                    )
        import androidx.annotation.RequiresApi
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.material3.MaterialTheme
        import androidx.compose.material3.Surface
        import androidx.compose.material3.Text
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.tooling.preview.Preview
        import androidx.work.BackoffPolicy
        import androidx.work.PeriodicWorkRequestBuilder
        import androidx.work.WorkManager
        import com.example.androidsystemchecker.ui.theme.AndroidSystemCheckerTheme
        import java.time.Duration
        import java.util.concurrent.TimeUnit
        import androidx.compose.foundation.layout.Arrangement
        import androidx.compose.foundation.layout.Column
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.material3.Button
        import androidx.compose.material3.ExperimentalMaterial3Api
        import androidx.compose.material3.Text
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.core.app.ActivityCompat
        import com.example.androidsystemchecker.ui.theme.AndroidSystemCheckerTheme

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
        setContent {
            AndroidSystemCheckerTheme {
                LaunchedEffect(key1 = Unit) {
                    val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(
                        repeatInterval = 15,
                        repeatIntervalTimeUnit = TimeUnit.MINUTES,
                    ).setBackoffCriteria(
                        backoffPolicy = BackoffPolicy.LINEAR,
                        duration = Duration.ofSeconds(15)
                    ).build()

                    val workManager = WorkManager.getInstance(applicationContext)
                    workManager.enqueue(workRequest)
                }

                setContent {
                    AndroidSystemCheckerTheme {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Button(onClick = {
                                Intent(applicationContext, ForegroundService::class.java).also {
                                    it.action = ForegroundService.Actions.START.toString()
                                    startService(it)
                                }
                            }) {
                                Text(text="Star Service")
                            }

                            Button(onClick = {
                                Intent(applicationContext, ForegroundService::class.java).also {
                                    it.action = ForegroundService.Actions.STOP.toString()
                                    startService(it)
                                }
                            }) {
                                Text(text="Stop Service")
                            }
                        }
                    }
                }
            }
        }