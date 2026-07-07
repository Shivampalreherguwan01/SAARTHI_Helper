package com.saarthi.helper.voice

import android.content.Context
import android.speech.tts.TextToSpeech

class VoiceManager(
    context: Context
) : TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)

    private var ready = false

    private var currentPack = VoicePacks.DEFAULT

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            ready = true

            applyVoice(currentPack)

        }

    }

    fun applyVoice(pack: VoicePack) {

        currentPack = pack

        if (!ready) return

        tts.language = pack.locale

        tts.setSpeechRate(pack.speed)

        tts.setPitch(pack.pitch)

    }

    fun speak(text: String) {

        if (!ready) return

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
