package com.saarthi.helper.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class Speaker(context: Context) {

    private val tts = TextToSpeech(context) {

        if (it == TextToSpeech.SUCCESS) {
            tts.language = Locale("hi", "IN")
        }

    }

    fun speak(text: String) {

        tts.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "SAARTHI"
        )

    }

    fun stop() {
        tts.stop()
    }

    fun destroy() {
        tts.stop()
        tts.shutdown()
    }

}
