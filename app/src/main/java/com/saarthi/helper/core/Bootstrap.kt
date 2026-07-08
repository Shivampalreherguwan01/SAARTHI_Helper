package com.saarthi.helper.core

import android.content.Context
import com.saarthi.helper.network.SocketService
import com.saarthi.helper.plugins.PluginManager
import com.saarthi.helper.plugins.SpeakPlugin
import com.saarthi.helper.plugins.VoicePlugin
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

        plugins.startAll()

        socket.connect(
            "ws://192.168.1.100:8765"
        )

    }

    fun stop() {

        socket.disconnect()

        plugins.stopAll()

    }

}
