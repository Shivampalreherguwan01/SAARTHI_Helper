package com.saarthi.helper.plugins

import android.content.Context
import com.saarthi.helper.model.Message
import com.saarthi.helper.voice.VoiceManager

class SpeakPlugin(

    context: Context

) : Plugin {

    private val voice =
        VoiceManager(context)

    override fun start() {

    }

    override fun stop() {

        voice.destroy()

    }

    override fun handle(
        message: Message
    ): Boolean {

        if (message.type != "speak") {

            return false

        }

        voice.speak(
            message.text
        )

        return true

    }

}
