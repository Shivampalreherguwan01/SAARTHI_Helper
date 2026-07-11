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

    private var recorder: AudioRecorder? = null
    private var recordedFile: File? = null

    init {
        recognizer.setRecognitionListener(
            object : RecognitionListener {

                override fun onReadyForSpeech(params: Bundle?) {
                    startRecording()
                }

                override fun onResults(results: Bundle?) {

                    val audioFile = stopRecording()

                    val list =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    if (!list.isNullOrEmpty()) {
                        onResult(
                            list[0],
                            audioFile?.absolutePath
                        )
                    }
                }

                override fun onError(error: Int) {
                    stopRecording()
                }

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            }
        )
    }

    private fun createVoiceFile(): File {

        val dir = File(
            context.filesDir,
            "voice"
        )

        dir.mkdirs()

        return File(
            dir,
            "latest.wav"
        )
    }

    private fun startRecording() {

        val file = createVoiceFile()

        val audioRecorder =
            AudioRecorder(file)

        if (audioRecorder.start()) {
            recorder = audioRecorder
            recordedFile = file
        }
    }

    private fun stopRecording(): File? {

        val file =
            recorder?.stop()

        recorder = null

        return file ?: recordedFile?.takeIf {
            it.exists() && it.length() > 44
        }
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
        stopRecording()
        recognizer.stopListening()
    }

    fun destroy() {
        stopRecording()
        recognizer.destroy()
    }
}
