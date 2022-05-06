package com.example.consumenoretrofit.data.local

import android.util.Log
import com.example.consumenoretrofit.data.models.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
class MainDataSource {
//     fun getInfo(): String {
//        var response = ""
//        try {
//            CoroutineScope(Dispatchers.IO).launch {
//                val client = URL("https://reqres.in/api/users?page=2")
//                    .openConnection() as HttpURLConnection
//                try {
//                    val data = client.inputStream.bufferedReader().use {
//                        response = it.readText()
//                    }
//                } catch (e: Exception) {
//                    response=e.printStackTrace().toString()
//                    client.disconnect()
//                }
//            }
//        } catch (e: Exception) {
//            response=e.printStackTrace().toString()
//        }
//    return response
//    }
    fun getInfo(): MutableList<Data> {
    val dataModel: MutableList<Data> = mutableListOf()
    var response = mutableListOf<Data>()
    try {
        CoroutineScope(Dispatchers.IO).launch {
            val client = URL("https://reqres.in/api/users?page=2")
                .openConnection() as HttpURLConnection
            try {
                var i = ""
                val data = client.inputStream.bufferedReader().use {
                    i = it.readText()
                }
                Log.e("getInfo1: ",i.toString() )
                val json = JSONObject(i)
                val array = json.getJSONArray("data")
                for (i in 0 until array.length()) {
                    val temp = array.getJSONObject(i)
                    dataModel.add(Data(temp.getInt("id"), temp.getString("email"), temp.getString("first_name"),
                        temp.getString("last_name"), temp.getString("avatar"))
                    )
                }
                response = dataModel
                Log.e("getInfo2: ", response.toString() )
            } catch (e: Exception) {
                client.disconnect()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return response
    }

}

