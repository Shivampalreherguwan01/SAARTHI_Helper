package com.saarthi.helper.network

import android.util.Log
import com.saarthi.helper.model.Message
import com.saarthi.helper.router.MessageRouter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit

class SocketService(

    private val router: MessageRouter

) {

    private val client =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .pingInterval(20, TimeUnit.SECONDS)
            .build()

    private var socket: WebSocket? = null

    fun connect(url: String) {

        Log.d("SAARTHI", "CONNECTING TO: $url")

        val request =
            Request.Builder()
                .url(url)
                .build()

        socket =
            client.newWebSocket(
                request,
                object : WebSocketListener() {

                    override fun onOpen(
                        webSocket: WebSocket,
                        response: Response
                    ) {

                        Log.d("SAARTHI", "SOCKET CONNECTED")

                    }

                    override fun onMessage(
                        webSocket: WebSocket,
                        text: String
                    ) {

                        Log.d("SAARTHI", "RECEIVED <- $text")

                        try {

                            val message =
                                Message.fromJson(text)

                            router.dispatch(message)

                        } catch (e: Exception) {

                            Log.e(
                                "SAARTHI",
                                "PARSE ERROR",
                                e
                            )

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

                        Log.d("SAARTHI", "SOCKET CLOSING")

                        webSocket.close(
                            1000,
                            null
                        )

                    }

                    override fun onFailure(
                        webSocket: WebSocket,
                        t: Throwable,
                        response: Response?
                    ) {

                        Log.e(
                            "SAARTHI",
                            "SOCKET FAILURE",
                            t
                        )

                    }

                }

            )

    }

    fun send(message: Message) {

        val json = message.toJson()

        Log.d("SAARTHI", "SEND -> $json")

        val result = socket?.send(json)

        Log.d("SAARTHI", "SEND RESULT -> $result")

    }

    fun disconnect() {

        Log.d("SAARTHI", "SOCKET DISCONNECTED")

        socket?.close(
            1000,
            "Bye"
        )

    }

}

