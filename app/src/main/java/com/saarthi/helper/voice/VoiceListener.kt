package com.saarthi.helper.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class VoiceListener(
    private val context: Context,
    private val onResult: (String, String?) -> Unit
) {

    private val handler =
        Handler(Looper.getMainLooper())

    private var running = false

    private val recognizer =
        SpeechRecognizer.createSpeechRecognizer(context)

    init {

        recognizer.setRecognitionListener(

            object : RecognitionListener {

                override fun onResults(
                    results: Bundle?
                ) {

                    val list =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    if (!list.isNullOrEmpty()) {
                        onResult(list[0], null)
                    }

                    restart()
                }

                override fun onError(error: Int) {
                    restart()
                }

                override fun onReadyForSpeech(
                    params: Bundle?
                ) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(
                    rmsdB: Float
                ) {}

                override fun onBufferReceived(
                    buffer: ByteArray?
                ) {}

                override fun onEndOfSpeech() {}

                override fun onPartialResults(
                    partialResults: Bundle?
                ) {}

                override fun onEvent(
                    eventType: Int,
                    params: Bundle?
                ) {}
            }
        )
    }

    private fun listen() {

        if (!running) {
            return
        }

        val intent =
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            )

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "hi-IN"
        )

        intent.putExtra(
            RecognizerIntent.EXTRA_PARTIAL_RESULTS,
            false
        )

        try {
            recognizer.startListening(intent)
        } catch (_: Exception) {
            restart()
        }
    }

    private fun restart() {

        if (!running) {
            return
        }

        handler.removeCallbacksAndMessages(null)

        handler.postDelayed(
            {
                listen()
            },
            500
        )
    }

    fun start() {

        running = true
        listen()
    }

    fun stop() {

        running = false

        handler.removeCallbacksAndMessages(null)

        recognizer.stopListening()
    }

    fun destroy() {

        running = false

        handler.removeCallbacksAndMessages(null)

        recognizer.destroy()
    }
}
