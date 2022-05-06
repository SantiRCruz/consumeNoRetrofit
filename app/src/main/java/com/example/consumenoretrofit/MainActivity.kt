package com.example.consumenoretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.consumenoretrofit.core.Result
import com.example.consumenoretrofit.data.local.MainDataSource
import com.example.consumenoretrofit.data.models.*
import com.example.consumenoretrofit.presentation.MainViewModel
import com.example.consumenoretrofit.presentation.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private var dataModel: MutableList<Data> = mutableListOf()
    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory(MainDataSource()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        getInfo()
        getInfo2()
//        postInfo()
//        putInfo()
//        deleteInfo()
    }

    private fun getInfo2() {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchUsers().collect {
                when(it){
                    is Result.Loading->{
                        Log.e("Loading", "Cargandooo")
                    }
                    is Result.Success ->{
                        Log.e("success", it.toString())
                    }
                    is Result.Failure ->{
                        Log.e("Failure", it.toString())
                    }
                }
            }
        }
    }

    private fun deleteInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val client =
                    URL("https://reqres.in/api/users/2").openConnection() as HttpURLConnection

                client.requestMethod = "DELETE"

                Log.e("deleteInfo: ",client.responseCode.toString() )
                client.inputStream.bufferedReader().use {
                    Log.e("deleteInfo: ", it.readText() )
                }

            }
        } catch (e: Exception) {
            Log.e("deleteInfo: ", e.message.toString())
        }
    }

    private fun postInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val client =
                    URL("https://jsonplaceholder.typicode.com/posts").openConnection() as HttpURLConnection

                val postData: ByteArray = NewPost("santiago", "bar",1).toString().toByteArray(StandardCharsets.UTF_8)

                client.requestMethod = "POST"
                client.doOutput = true
                client.setRequestProperty("Content-Type", "application/x-222=form-urlencoded")
                client.setRequestProperty("charset", "utf-8")
                client.setRequestProperty("Content-length", postData.size.toString())
                client.useCaches = false
                client.outputStream.write(postData)

                client.inputStream.bufferedReader().use {
                    Log.e("postInfo: ", it.readText() )
                }

            }
        } catch (e: Exception) {
            Log.e("postInfoF: ", e.message.toString())
        }
    }

    private fun putInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val client =
                    URL("https://jsonplaceholder.typicode.com/posts/1").openConnection() as HttpURLConnection

                val postData: ByteArray = UpdatePost(1,"santiago", "bar",1).toString().toByteArray(StandardCharsets.UTF_8)

                client.requestMethod = "PUT"
                client.doOutput = true
                client.setRequestProperty("Content-Type", "application/x-222=form-urlencoded")
                client.setRequestProperty("charset", "utf-8")
                client.setRequestProperty("Content-length", postData.size.toString())
                client.useCaches = false
                client.outputStream.write(postData)

                client.inputStream.bufferedReader().use {
                    Log.e("putInfo: ", it.readText() )
                }

            }
        } catch (e: Exception) {
            Log.e("putInfo: ", e.message.toString())
        }
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
                    }
                    Log.e("getInfo: ",i.toString() )
                    val json = JSONObject(i)
                    val array = json.getJSONArray("data")
                    for (i in 0 until array.length()) {
                        val temp = array.getJSONObject(i)
                        dataModel.add(Data(temp.getInt("id"), temp.getString("email"), temp.getString("first_name"),
                                temp.getString("last_name"), temp.getString("avatar"))
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