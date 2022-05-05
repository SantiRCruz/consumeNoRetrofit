package com.example.consumenoretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getInfo()
    }

    private fun getInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val client = URL("https://quotes.rest/qod.json")
                    .openConnection() as HttpURLConnection
                try {
                    val data = client.inputStream.bufferedReader().use {
                        Log.e("getInfo: ", it.readText() ) }

                } catch (e: Exception) {
                    client.disconnect()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}