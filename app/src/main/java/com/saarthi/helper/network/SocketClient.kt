package com.saarthi.helper.network

import android.util.Log
import com.saarthi.helper.model.Message
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit

class SocketClient {

    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .pingInterval(20, TimeUnit.SECONDS)
        .build()

    private var socket: WebSocket? = null

    var onConnected: (() -> Unit)? = null
    var onDisconnected: (() -> Unit)? = null
    var onMessage: ((Message) -> Unit)? = null

    fun connect(url: String) {

        val request = Request.Builder()
            .url(url)
            .build()

        socket = client.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(
                    webSocket: WebSocket,
                    response: Response
                ) {
                    Log.d("SAARTHI", "CONNECTED")
                    onConnected?.invoke()
                }

                override fun onMessage(
                    webSocket: WebSocket,
                    text: String
                ) {
                    try {
                        onMessage?.invoke(
                            Message.fromJson(text)
                        )
                    } catch (e: Exception) {
                        Log.e("SAARTHI", e.toString())
                    }
                }

                override fun onMessage(
                    webSocket: WebSocket,
                    bytes: ByteString
                ) {
                }

                override fun onClosing(
                    webSocket: WebSocket,
                    code: Int,
                    reason: String
                ) {
                    onDisconnected?.invoke()
                    webSocket.close(1000, null)
                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?
                ) {
                    Log.e("SAARTHI", t.toString())
                    onDisconnected?.invoke()
                }

            }
        )

    }

    fun send(message: Message) {
        socket?.send(message.toJson())
    }

    fun disconnect() {
        socket?.close(1000, "bye")
    }

}
