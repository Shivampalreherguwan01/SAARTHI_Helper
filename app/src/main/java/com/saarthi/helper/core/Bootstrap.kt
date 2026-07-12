package com.saarthi.helper.core

import android.content.Context
import com.saarthi.helper.network.SocketService
import com.saarthi.helper.plugins.PluginManager
import com.saarthi.helper.plugins.SpeakPlugin
import com.saarthi.helper.plugins.VoicePlugin
import com.saarthi.helper.plugins.VoiceEnrollmentPlugin
import com.saarthi.helper.router.MessageRouter

class Bootstrap(

    private val context: Context,

    private val router: MessageRouter

) {

    private val plugins = PluginManager()

    private val socket = SocketService(router)

    fun start() {

        router.attach(plugins)

        plugins.register(
            SpeakPlugin(context)
        )

        plugins.register(
            VoicePlugin(
                context,
                socket
            )
        )

        plugins.register(
            VoiceEnrollmentPlugin()
        )

        plugins.startAll()

        val preferences =
            context.getSharedPreferences(
                "saarthi_runtime",
                Context.MODE_PRIVATE
            )

        val serverUrl =
            preferences.getString(
                "server_url",
                "ws://127.0.0.1:8765"
            ) ?: "ws://127.0.0.1:8765"

        socket.connect(serverUrl)
    }

    fun stop() {

        socket.disconnect()

        plugins.stopAll()
    }
}
