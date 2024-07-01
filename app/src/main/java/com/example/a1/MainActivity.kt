package com.example.a1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val raspberryPiIp = "http://<IP_DO_RASPBERRY_PI>:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOn: Button = findViewById(R.id.buttonOn)
        val buttonOff: Button = findViewById(R.id.buttonOff)

        buttonOn.setOnClickListener {
            sendRequest("$raspberryPiIp/led/on")
        }

        buttonOff.setOnClickListener {
            sendRequest("$raspberryPiIp/led/off")
        }
    }

    private fun sendRequest(url: String) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.string()?.let { responseBody ->
                    runOnUiThread {
                        // Mostrar uma mensagem para o usuário
                        // Toast.makeText(this@MainActivity, responseBody, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}

