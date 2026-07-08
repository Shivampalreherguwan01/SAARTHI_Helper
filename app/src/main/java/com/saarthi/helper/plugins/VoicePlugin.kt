package com.saarthi.helper.plugins

import android.content.Context
import com.saarthi.helper.model.Message
import com.saarthi.helper.network.SocketService
import com.saarthi.helper.voice.VoiceListener

class VoicePlugin(

    context: Context,

    private val socket: SocketService

) : Plugin {

    private val listener =
        VoiceListener(context) { text, audio ->

            socket.send(

                Message(

                    type = "voice",

                    text = text,

                    data = audio ?: ""

                )

            )

        }

    override fun start() {

        listener.start()

    }

    override fun stop() {

        listener.destroy()

    }

    override fun handle(

        message: Message

    ): Boolean {

        return false

    }

}

