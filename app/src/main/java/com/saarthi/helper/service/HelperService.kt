package com.saarthi.helper.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

import com.saarthi.helper.network.SocketClient
import com.saarthi.helper.voice.Speaker
import com.saarthi.helper.voice.VoiceListener

class HelperService : Service() {

    private lateinit var speaker: Speaker
    private lateinit var voice: VoiceListener
    private lateinit var socket: SocketClient

    override fun onCreate() {
        super.onCreate()

        createChannel()

        val notification =
            Notification.Builder(this, "SAARTHI_HELPER")
                .setContentTitle("SAARTHI Helper")
                .setContentText("Listening...")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .build()

        startForeground(1, notification)

        speaker = Speaker(this)

        socket = SocketClient()

        socket.onMessageReceived = { message ->

            Log.d("SAARTHI", "Server: $message")

            if (message.contains("\"type\":\"speak\"")) {

                val text =
                    Regex("\"text\"\\s*:\\s*\"([^\"]*)\"")
                        .find(message)
                        ?.groupValues
                        ?.get(1)

                if (!text.isNullOrEmpty()) {
                    speaker.speak(text)
                }
            }
        }

        socket.connect("ws://192.168.1.100:8765")

        voice = VoiceListener(this) { text ->

            Log.d("SAARTHI", "User: $text")

            socket.send(
                """
                {
                  "type":"voice",
                  "text":"$text",
                  "data":""
                }
                """.trimIndent()
            )
        }

        voice.start()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        return START_STICKY
    }

    override fun onDestroy() {

        voice.destroy()
        speaker.destroy()

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                "SAARTHI_HELPER",
                "SAARTHI Helper",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager =
                getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(channel)
        }
    }
}
