package com.saarthi.helper.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.io.File

class VoiceListener(

    private val context: Context,

    private val onResult: (String, String?) -> Unit

) {

    private val recognizer =
        SpeechRecognizer.createSpeechRecognizer(context)

    init {

        recognizer.setRecognitionListener(

            object : RecognitionListener {

                override fun onResults(results: Bundle?) {

                    val list =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    if (!list.isNullOrEmpty()) {

                        val audioFile = createVoiceFile()

                        onResult(
                            list[0],
                            audioFile.absolutePath
                        )

                    }

                }

                override fun onReadyForSpeech(params: Bundle?) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {}

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}

            }

        )

    }

    private fun createVoiceFile(): File {

        val dir =
            File(context.filesDir, "voice")

        dir.mkdirs()

        return File(
            dir,
            "latest.wav"
        )

    }

    fun start() {

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

        recognizer.startListening(intent)

    }

    fun stop() {

        recognizer.stopListening()

    }

    fun destroy() {

        recognizer.destroy()

    }

}
