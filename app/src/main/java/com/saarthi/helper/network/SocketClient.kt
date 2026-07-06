package com.saarthi.helper.network

import android.util.Log
import okhttp3.*
import okio.ByteString

class SocketClient {

    private var webSocket: WebSocket? = null

    fun connect(serverUrl: String) {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(serverUrl)
            .build()

        webSocket = client.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(webSocket: WebSocket, response: Response) {
                    Log.d("SAARTHI", "Connected")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    Log.d("SAARTHI", "Message: $text")
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {}

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    webSocket.close(1000, null)
                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?
                ) {
                    Log.e("SAARTHI", "Connection Failed", t)
                }
            }
        )
    }

    fun send(text: String) {
        webSocket?.send(text)
    }
}
