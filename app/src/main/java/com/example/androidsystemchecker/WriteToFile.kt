package com.example.androidsystemchecker

import org.json.JSONObject


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.io.File



    fun writeData(type: String , Calendar : String , value:String) {
        val jsonObject = JSONObject()
        jsonObject.put("type", type)
        jsonObject.put("time", Calendar)
        jsonObject.put("value", value)
        val fileName = "temp.json"
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        val folder = context.filesDir
        val file = File(folder, fileName)
        try {
            file.createNewFile()
            file.appendText(jsonObject.toString())
            file.appendText("\n")
        } catch (e: Exception) {
            Log.e("FileWriter", "Error writing to file: ${e.message}")
        }
    }



