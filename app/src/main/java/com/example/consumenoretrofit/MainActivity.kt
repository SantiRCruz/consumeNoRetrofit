package com.example.consumenoretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import com.example.consumenoretrofit.data.models.Data
import com.example.consumenoretrofit.data.models.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var dataModel: MutableList<Data> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getInfo()
    }

    private fun getInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val client = URL("https://reqres.in/api/users?page=2")
                    .openConnection() as HttpURLConnection
                try {
                    var i = ""
                    val data = client.inputStream.bufferedReader().use {
                        i = it.readText()
//                        Log.e("getInfo: ", i)
                    }
                    val json = JSONObject(i)
//                    Log.e("getJson: ", json.toString())
                    val array = json.getJSONArray("data")
//                    Log.e("getData: ", array.toString())
                    for (i in 0 until array.length()) {
                        val temp = array.getJSONObject(i)
                        dataModel.add(
                            Data(
                                temp.getInt("id"),
                                temp.getString("email"),
                                temp.getString("first_name"),
                                temp.getString("last_name"),
                                temp.getString("avatar")
                            )
                        )
                    }
                    Log.e("Object", dataModel.toString())
                } catch (e: Exception) {
                    client.disconnect()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}