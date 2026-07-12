package com.saarthi.helper.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log

class VoiceListener(
    private val context: Context,
    private val onResult: (String, String?) -> Unit
) {

    private val recognizer =
        SpeechRecognizer.createSpeechRecognizer(context)

    private val handler =
        android.os.Handler(android.os.Looper.getMainLooper())

    private var running = false

    init {
        recognizer.setRecognitionListener(
            object : RecognitionListener {

                override fun onReadyForSpeech(params: Bundle?) {
                    Log.d("SAARTHI", "LISTENING")
                }

                override fun onResults(results: Bundle?) {

                    val list =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    if (!list.isNullOrEmpty()) {
                        val text = list[0]

                        Log.d("SAARTHI", "HEARD: $text")

                        onResult(text, null)
                    }

                    restart()
                }

                override fun onError(error: Int) {
                    Log.d("SAARTHI", "VOICE ERROR: $error")
                    restart()
                }

                override fun onBeginningOfSpeech() {
                    Log.d("SAARTHI", "SPEECH START")
                }

                override fun onEndOfSpeech() {
                    Log.d("SAARTHI", "SPEECH END")
                }

                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            }
        )
    }

    private fun listen() {

        if (!running) return

        val intent =
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "hi-IN"
        )

        try {
            recognizer.startListening(intent)
        } catch (e: Exception) {
            Log.e("SAARTHI", "VOICE START ERROR", e)
            restart()
        }
    }

    private fun restart() {

        if (!running) return

        handler.removeCallbacksAndMessages(null)

        handler.postDelayed(
            { listen() },
            1000
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
